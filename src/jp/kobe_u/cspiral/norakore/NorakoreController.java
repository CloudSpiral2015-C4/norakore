package jp.kobe_u.cspiral.norakore;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

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

    public String registerNyavatar(String userID, String name, String type,
            String picture, double lon, double lat) {
        NyavatarDetail nya = new NyavatarDetail();
        nya.setName(name);
        nya.setType(type);

        String picid = saveImage(picture, "picture");
        nya.setPictureID(picid);

        Location loc = new Location();
        loc.setLon(lon);
        loc.setLat(lat);
        nya.setLocation(loc);

        nya.determineParams(userID); // 欠落パラメータ補完

        // TODO: 重複チェック；名前はともかく、pictureぐらいはチェック必要だろう
        DBObject dbo = nya.toDBObject();
        NyavatarColl.insert(dbo);
        String nya_id = dbo.get("_id").toString();

        // 登録するユーザを取得
        // TODO:
        // DBObject query = new BasicDBObject("_id", new ObjectId(userID));
        DBObject query = new BasicDBObject("_id", userID);
        DBObject userdbo = UserColl.findOne(query);
        if (userdbo == null) return null;
        // ユーザのにゃばたーリストに登録したにゃばたーを追加する
        BasicDBList list = (BasicDBList)userdbo.get("nyavatarList");
        list.add(nya_id);
        // 更新したにゃばたーリストをユーザに適応する
        userdbo.put("nyavatarList", list);
        UserColl.update(query, userdbo);

        return nya_id;
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
