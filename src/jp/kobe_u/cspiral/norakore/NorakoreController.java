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

	private DBCollection NyavatarColl;

	public NorakoreController() {
        this.NyavatarColl = DBUtils.getInstance().getDb().getCollection(NyavatarColl_Name);
	}

    public Report searchNyavatar(double x, double y) {
        final double search_area = 10000;
        Report result = new Report();
        List<Nyavatar> list = new ArrayList<Nyavatar>();

        DBCursor cursor = NyavatarColl.find();
        for (DBObject nya : cursor) {
            Location loc = new Location((DBObject)nya.get("location"));
            double dx = loc.getX() - x;
            double dy = loc.getY() - y;
            if (Math.pow(dx, 2) + Math.pow(dy, 2) < Math.pow(search_area, 2)) {
                list.add(new Nyavatar(nya));
            }
        }

        result.setList(list);
        return result;
    }

    public String registerNyavatar(NyavatarDetail nyavatar) {
        // TODO:picture, locationの存在チェック
        nyavatar.determineParams();

        // TODO: 重複チェック；名前はともかく、pictureぐらいはチェック必要だろう
        DBObject dbo = nyavatar.toDBObject();
        NyavatarColl.insert(dbo);
        return ((ObjectId)dbo.get("_id")).toString();
    }

	// public Report getReport(int n) {
	// 	DBObject query = new BasicDBObject();

	// 	Report report = new Report();
	// 	List<Like> likes = new ArrayList<Like>();
	// 	List<Comment> comments = new ArrayList<Comment>();

	// 	DBCursor cursor = LIKE_COLLECTION.find(query);

	// 	// for (DBObject like : cursor) {
	// 		// likes.add(new Like((Date)like.get("date")));
	// 	// }

	// 	report.setTotalLike(cursor.count());
	// 	DBObject sort = new BasicDBObject("_id", -1);
	// 	cursor = COMMENT_COLLECTION.find(query).sort(sort).limit(n);
	// 	for (DBObject comment : cursor) {
	// 		comments.add(new Comment(
	// 				(Date)comment.get("date"), (String)comment.get("message")));
	// 	}

	// 	report.setLikes(likes);
	// 	report.setComments(comments);
	// 	return report;
	// }


	// public String savePhoto(String photoData) {
	// 	DBObject dbo = new BasicDBObject("src", photoData);
	// 	PHOTO_COLLECTION.save(dbo);
	// 	String id = dbo.get("_id").toString();
	// 	return id;
	// }


	// public ByteArrayOutputStream getPhoto(String id) {
	// 	DBObject query = new BasicDBObject("_id", new ObjectId(id));
	// 	DBObject o = PHOTO_COLLECTION.findOne(query);
	// 	if (o == null) {
	// 		return null;
	// 	}
	// 	String src = (String)o.get("src");
	// 	src = src.split(",")[1];
	// 	byte[] bytes = Base64.decode(src);
	// 	try {
	// 		BufferedImage bImage = ImageIO.read(new ByteArrayInputStream(bytes));
	// 		ByteArrayOutputStream baos = new ByteArrayOutputStream();;
	// 		ImageIO.write(bImage, "png", baos);
	// 		return baos;
	// 	} catch (IOException e) {
	// 		// TODO 自動生成された catch ブロック
	// 		e.printStackTrace();
	// 	}
	// 	 return null;
	// }


	// public List<String> getPhotoList(int n) {
	// 	List<String> list = new ArrayList<>();
	// 	DBObject orderBy = new BasicDBObject("$natural", -1);
	// 	DBCursor cursor = PHOTO_COLLECTION.find().sort(orderBy).limit(n);
	// 	for (DBObject o : cursor) {
	// 		list.add(o.get("_id").toString());
	// 	}
	// 	return list;
	// }

}
