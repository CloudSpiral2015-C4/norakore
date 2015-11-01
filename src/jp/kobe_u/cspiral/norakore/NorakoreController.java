package jp.kobe_u.cspiral.norakore;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import jp.kobe_u.cspiral.norakore.model.*;
import jp.kobe_u.cspiral.norakore.util.DBUtils;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sun.jersey.core.util.Base64;

public class NorakoreController {
	private final String NyavatarColl_Name = "nyavatar";
	private final String UserColl_Name = "user";
	private final String PictureColl_Name = "picture";
	private final String IconColl_Name = "icon";

	private DBCollection NyavatarColl;
	private DBCollection UserColl;
	private DBCollection PictureColl;
	private DBCollection IconColl;

	public NorakoreController() {
        this.NyavatarColl = DBUtils.getInstance().getDb().getCollection(NyavatarColl_Name);
        this.UserColl = DBUtils.getInstance().getDb().getCollection(UserColl_Name);
        this.PictureColl = DBUtils.getInstance().getDb().getCollection(PictureColl_Name);
        this.IconColl = DBUtils.getInstance().getDb().getCollection(IconColl_Name);
	}

    public NyavatarList searchNyavatar(double lon, double lat) {
        final double search_area = 10000;
        NyavatarList result = new NyavatarList();
        List<Nyavatar> list = new ArrayList<Nyavatar>();

        DBCursor cursor = NyavatarColl.find();
        for (DBObject nya : cursor) {
            // TODO: mongoのクエリ書く, 四角形範囲クエリにする
            list.add(new Nyavatar(nya));
        }

        result.setList(list);
        return result;
    }

    public NyavatarList getUsersNyavatar(String userID) throws Exception {
        // get user's nyavatar list
        DBObject user = User.getDBObject(userID);
        BasicDBList id_list = (BasicDBList)user.get("nyavatarList");
        if (id_list == null) throw new Exception("user's nyavatarList is not found.");

        // generate nyavatar list from id list
        List<Nyavatar> ny_list = new ArrayList<Nyavatar>();
        for(Object id: id_list) {
            try {
                ny_list.add(Nyavatar.generateNyavatar((String)id));
            } catch(Exception e) {
                throw new Exception(MessageFormat.format("User has lost nyavatar. id={0}", (String)id));
            }
        }

        NyavatarList result = new NyavatarList();
        result.setList(ny_list);
        return result;
    }

    public NyavatarDetail getNyavatarDetail(String nyavatarID, String userID){
    	NyavatarDetail result = new NyavatarDetail();

    	DBObject query = new BasicDBObject("_id",new ObjectId(nyavatarID));
    	DBObject queryResult = NyavatarColl.findOne(query);

    	// TODO: likeUsersの中にuserIDが含まれるかのチェック⇒ふくまれていいたら、isLiked=true
    	result.setNyavatarID(queryResult.get("_id").toString());
    	result.setName((String)queryResult.get("name"));
    	result.setType((String)queryResult.get("type"));
    	result.setPictureID((String)queryResult.get("pictureID"));
    	result.setIconID((String)queryResult.get("iconID"));
    	result.setDate((Date)queryResult.get("date"));
    	result.setLocation(new Location((DBObject)queryResult.get("location")));
    	// TODO: LostCatIDにネコサーチAPIを投げて類似猫があれば、LostCatsこれくしょんに追加してそのIDを追加
    	// TODO: 広告文章をsayに追加する話は無視
    	result.setSay((String)queryResult.get("say"));
    	return result;
    }

    public int likeNyavatar(String nyavatarID, String userID) throws Exception{
        DBObject nya = Nyavatar.getDBObject(nyavatarID);

        // get nyavatar's likeUser list
        BasicDBList like_list = (BasicDBList)nya.get("likeUserList");
        if (like_list == null) throw new Exception("The nyavatar has no likeUserList.");

        // add user to the list
        if (like_list.contains(userID)) throw new Exception("user already like the nyavatar.");
        like_list.add(userID);
        nya.put("likeUserList", like_list);
        Nyavatar.updateNyavatar(nya);

    	return like_list.size();
    }

    public RegisterResult registerNyavatar(String userID, String name, String type,
            String picture, double lon, double lat) throws Exception {
        NyavatarDetail nya = new NyavatarDetail();
        nya.setName(name);
        nya.setType(type);

        if (picture == null || picture.length() == 0) throw new Exception(
                "Param:picture is not specified.");
        String picid = saveImage(picture, "picture");
        if (picid == "") throw new Exception("saveImage failed.");
        nya.setPictureID(picid);

        // iconIDを２つのうちどちらかランダムに。アイコン画像が増えたら、適宜対応
        String iconid = "nullID";
        Random rnd = new Random();
        int ran = rnd.nextInt(3);
        switch(ran){
        case 0:
        case 1:
        	iconid = "563374c731b1b0e407093a9f";
        	break;
        case 2:
        	iconid = "563374d831b1b0e408093a9f";
        	break;
        }
        nya.setIconID(iconid);

        nya.setLocation(new Location(lon, lat));
        nya.setSay("お腹すいたにゃぁ～");

        nya.determineParams(userID); // 欠落パラメータ補完

        // TODO: 重複チェック；名前はともかく、pictureぐらいはチェック必要だろう
        DBObject dbo = nya.toDBObject();
        NyavatarColl.insert(dbo);
        String nya_id = dbo.get("_id").toString();

        // 登録するユーザににゃばたーとかつおを付与
        DBObject user = User.getDBObject(userID);
        BasicDBList list = User.addNyavatar(user, nya_id);
        int bonitos = User.addBonitos(user, 10);
        User.updateUser(user);

        RegisterResult result = new RegisterResult();
        result.setNyavatarID(nya_id);
        result.setBonitos(bonitos);

        return result;
    }

    public UserResult getUserInfo(String userID) throws Exception{
    	UserResult result = new UserResult();

        DBObject user = User.getDBObject(userID);

    	result.setUserID((String)user.get("_id"));
    	result.setName((String)user.get("name"));
    	result.setBonitos((Integer)user.get("bonitos"));

    	// TODO: 重複無しに変える処理が必要（アイテム実装後）
    	BasicDBList list = (BasicDBList)user.get("itemList");
    	List<String> itemList = new ArrayList<String>();
    	for(Object el: list) {
    	     itemList.add((String) el);
    	}
    	result.setItemIDList(itemList);

    	// nyavatarIDListを使ってiconIDListを作成
    	BasicDBList nyavatarlist = User.getNyavatarList(user);
    	List<String> iconList = new ArrayList<String>();
    	for(Object el: nyavatarlist) {
            DBObject nya = Nyavatar.getDBObject((String)el);
    		iconList.add((String)nya.get("iconID"));
    	}
    	result.setIconIDList(iconList);

    	return result;
    }

	public String saveImage(String data, String res) {
		DBObject dbo = new BasicDBObject("src", data);
        if (res.equals("picture")){
            PictureColl.save(dbo);
        }else if (res.equals("icon")){
            IconColl.save(dbo);
        }else return "";
		String id = dbo.get("_id").toString();
		return id;
	}

	public ByteArrayOutputStream getImage(String id, String res) {
		DBObject query = new BasicDBObject("_id", new ObjectId(id));
        String type;
        DBObject o;
        if (res.equals("picture")){
            o = PictureColl.findOne(query);
            type = "jpg";
        }else if (res.equals("icon")){
            o = IconColl.findOne(query);
            type = "png";
        }else return null;
		if (o == null) return null;

		String src = (String)o.get("src");
		src = src.split(",")[1];
		byte[] bytes = Base64.decode(src);
		try {
			BufferedImage bImage = ImageIO.read(new ByteArrayInputStream(bytes));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bImage, type, baos);
			return baos;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		 return null;
	}
}
