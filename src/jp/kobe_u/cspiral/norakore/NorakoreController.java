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
        final double search_range = 0.01;
        NyavatarList result = new NyavatarList();
        List<Nyavatar> list = new ArrayList<Nyavatar>();

        // (lon-0.01 < location.lon < lon+0.01) and (lat-0.01 < location.lat < lat+0.01)
        DBObject query = new BasicDBObject("location.lon", new BasicDBObject("$gt",lon-search_range).append("$lt", lon+search_range)).
        		append("location.lat", new BasicDBObject("$gt",lat-search_range).append("$lt", lat+search_range));
        DBCursor cursor = NyavatarColl.find(query);
        for (DBObject nya : cursor) {
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
    	Double bonitos = (Double)user.get("bonitos");
    	result.setBonitos(bonitos.intValue());

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

    public RegisterResult Scanya(String userID, double lon, double lat, String itemID) throws Exception {

    	// 対象となるにゃばたーリストを取得（api/nyavatarと同じ）
    	final double search_range = 0.01;
        DBObject queryNya = new BasicDBObject("location.lon", new BasicDBObject("$gt",lon-search_range).append("$lt", lon+search_range)).
        		append("location.lat", new BasicDBObject("$gt",lat-search_range).append("$lt", lat+search_range));
        DBCursor cursorNya = NyavatarColl.find(queryNya);
        if (cursorNya == null) throw new Exception("user's nyavatarList is not found.");

        // 対象となるユーザを取得
        DBObject queryUser = new BasicDBObject("_id",userID);
        DBObject objectUser = UserColl.findOne(queryUser);
        if (objectUser == null) throw new Exception("Specified user is not found.");
        BasicDBList nyavatarIDList = (BasicDBList)objectUser.get("nyavatarList");

        // 対象となるにゃばたーのうち、ユーザの持つにゃばたー以外をnyavatarListにadd
        List<String> nyavatarList = new ArrayList<String>();
        for (DBObject nya : cursorNya) {
        	if(!nyavatarIDList.contains(nya.get("_id").toString())){
        		nyavatarList.add(nya.get("_id").toString());
        	}
        }

        String getNyavatarID = "nullID";
        Double bonitos = 0.0;

        if(nyavatarList.size() > 0){
        	// nyavatarListのうちどれかをランダムに１つ選出し、ユーザの持つにゃばたーリストに追加
            // TODO: デモのために確実に１つ手に入るようにしてるが、本来はにゃばたー自体に確率を持たせるべき
        	Random rnd = new Random();
        	int ran = rnd.nextInt(nyavatarList.size()+1);
        	if(ran == 0) ran=1;
        	getNyavatarID = nyavatarList.get(ran-1);
        	nyavatarIDList.add(getNyavatarID);

        	// 更新したにゃばたーリストをユーザに適応する
            objectUser.put("nyavatarList", nyavatarIDList);
            // 所持かつお数を追加（今は固定10かつお）
            bonitos = (Double)objectUser.get("bonitos") + 10;
            objectUser.put("bonitos", bonitos);
            UserColl.update(queryUser, objectUser);
        }

        RegisterResult result = new RegisterResult();
        result.setNyavatarID(getNyavatarID);
        result.setBonitos(bonitos.intValue());

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
