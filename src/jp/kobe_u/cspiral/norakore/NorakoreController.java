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
        NyavatarList result = new NyavatarList();
        try {
            // retrieve the specified user's DBObject
            //DBObject query = new BasicDBObject("_id", new ObjectId(userID));
        	DBObject query = new BasicDBObject("_id", userID);
            DBObject userdbo = UserColl.findOne(query);
            if (userdbo == null) throw new Exception("Specified user is not found.");

            // get user's nyavatar list
            BasicDBList id_list = (BasicDBList)userdbo.get("nyavatarList");
            if (id_list == null) throw new Exception("user's user is not found.");

            // generate nyavatar list from id list
            List<Nyavatar> ny_list = new ArrayList<Nyavatar>();
            for(Object id: id_list) {
                ObjectId oid = new ObjectId((String)id);
                DBObject ny_dbo = NyavatarColl.findOne(new BasicDBObject("_id", oid));
                if (ny_dbo == null) throw new Exception("There is lost-nyavatar on db.");
                ny_list.add(new Nyavatar(ny_dbo));
            }

            // generate result object
            result.setList(ny_list);
        } catch (IllegalArgumentException e) {
            throw new Exception(MessageFormat.format("Invalid userID, userID={0}", userID));
        }
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
        int ran = rnd.nextInt(2);
        switch(ran){
        case 0:
        	iconid = "563374c731b1b0e407093a9f";
        case 1:
        	iconid = "563374d831b1b0e408093a9f";
        }
        nya.setIconID(iconid);

        Location loc = new Location();
        loc.setLon(lon);
        loc.setLat(lat);
        nya.setLocation(loc);
        nya.setSay("お腹すいたにゃぁ～");

        nya.determineParams(userID); // 欠落パラメータ補完

        // TODO: 重複チェック；名前はともかく、pictureぐらいはチェック必要だろう
        DBObject dbo = nya.toDBObject();
        NyavatarColl.insert(dbo);
        String nya_id = dbo.get("_id").toString();

        // 登録するユーザを取得
        // TODO: error handling
        //DBObject query = new BasicDBObject("_id", new ObjectId(userID));
        DBObject query = new BasicDBObject("_id", userID);
        DBObject userdbo = UserColl.findOne(query);
        if (userdbo == null) return null;
        // ユーザのにゃばたーリストに登録したにゃばたーを追加する
        BasicDBList list = (BasicDBList)userdbo.get("nyavatarList");
        list.add(nya_id);
        // 更新したにゃばたーリストをユーザに適応する
        userdbo.put("nyavatarList", list);
        // 所持かつお数を追加（今は固定10かつお）
        Double bonitos = (Double)userdbo.get("bonitos") + 10;
        userdbo.put("bonitos", bonitos);
        UserColl.update(query, userdbo);

        RegisterResult result = new RegisterResult();
        result.setNyavatarID(nya_id);
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
