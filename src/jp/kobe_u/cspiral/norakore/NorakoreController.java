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

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sun.jersey.core.util.Base64;

public class NorakoreController {
	private final String NyavatarColl_Name = "nyavatar";
	private final String PictureColl_Name = "picture";
	private final String IconColl_Name = "icon";

	private DBCollection NyavatarColl;
	private DBCollection PictureColl;
	private DBCollection IconColl;

	public NorakoreController() {
        this.NyavatarColl = DBUtils.getInstance().getDb().getCollection(NyavatarColl_Name);
        this.PictureColl = DBUtils.getInstance().getDb().getCollection(PictureColl_Name);
        this.IconColl = DBUtils.getInstance().getDb().getCollection(IconColl_Name);
	}

    public NyavatarList searchNyavatar(double x, double y) {
        final double search_area = 10000;
        NyavatarList result = new NyavatarList();
        List<Nyavatar> list = new ArrayList<Nyavatar>();

        DBCursor cursor = NyavatarColl.find();
        for (DBObject nya : cursor) {
            Location loc = new Location((DBObject)nya.get("location"));
            double dx = loc.getLon() - x;
            double dy = loc.getLat() - y;
            if (Math.pow(dx, 2) + Math.pow(dy, 2) < Math.pow(search_area, 2)) {
//                list.add(new Nyavatar(nya));
            }
        }
        // TODO: 四角形範囲クエリにする

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
        return dbo.get("_id").toString();
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
