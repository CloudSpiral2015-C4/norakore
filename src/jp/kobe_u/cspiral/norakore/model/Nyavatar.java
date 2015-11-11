package jp.kobe_u.cspiral.norakore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.MessageFormat;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.BasicDBList;
import org.bson.types.ObjectId;

import jp.kobe_u.cspiral.norakore.util.DBUtils;
import jp.kobe_u.cspiral.norakore.model.NyavatarDetail;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="nyavatar")
public class Nyavatar {

    // static member -----------------------------------------------------------
	private static DBCollection dbColl;
    static {
        dbColl = DBUtils.getInstance().getDb().getCollection("nyavatar");
    }

    public static Nyavatar generateNyavatar(DBObject dbo) throws Exception{
        Nyavatar nya = new Nyavatar();

        nya.nyavatarID = dbo.get("_id").toString();
        nya.name = (String)dbo.get("name");
        nya.pictureID = (String)dbo.get("pictureID");
        nya.iconID = (String)dbo.get("iconID");
        nya.date = (Date)dbo.get("date");
        nya.location = new Location((DBObject)dbo.get("location"));
        BasicDBList liker = (BasicDBList)dbo.get("likeUserList");
        if (liker == null) throw new Exception(
                "generate nyavatar failed; likeUserList is lost on DB document.");
        nya.like = liker.size();
        nya.lostCatID = (String)dbo.get("lostCatID");

        if (nya.nyavatarID == null) throw new Exception(
                "generate nyavatar failed; nyavatarID is lost on DB document.");
        if (nya.name == null) throw new Exception(
                "generate nyavatar failed; name is lost on DB document.");
        if (nya.pictureID == null) throw new Exception(
                "generate nyavatar failed; pictureID is lost on DB document.");
        if (nya.iconID == null) throw new Exception(
                "generate nyavatar failed; iconID is lost on DB document.");
        if (nya.date == null) throw new Exception(
                "generate nyavatar failed; date is lost on DB document.");
        if (nya.location == null) throw new Exception(
                "generate nyavatar failed; location is lost on DB document.");
        if (nya.lostCatID == null) throw new Exception(
                "generate nyavatar failed; lostCatID is lost on DB document.");

        return nya;
    }

    public static Nyavatar generateNyavatar(String id) throws Exception {
        DBObject dbo = Nyavatar.getDBObject(id);
        return Nyavatar.generateNyavatar(dbo);
    }

    // public static NyavatarDetail generateNyavatarDetail(DBObject dbo) {
    //     NyavatarDetail nyavatar = new NyavatarDetail(); 
    //     return nyavatar;
    // }

    // 指定されたidのにゃばたーのDBObjectを取得
    public static DBObject getDBObject(String id) throws Exception{
        DBObject query = new BasicDBObject("_id", new ObjectId(id));
        DBObject nya = dbColl.findOne(query);
        if (nya == null) throw new Exception(MessageFormat.format(
                "Specified nyavatar is not found. id={0}", id));
        return nya;
    }

    // ランダムにセリフを生成
    private static String[] SayList = {
        	"お腹すいたにゃぁ～",
        	"ん？ 一緒に遊んでくれるのかにゃ？",
        	"うにー…  マタタビのせいで酔っちゃったにゃぁ～//",
        	"にゃっ！？  尻尾は触っちゃダメっ！",
        	"…にゃにか用？",
        	"ふにゃぁ～  な～んか暇だにゃぁ・・・",
        	"「好奇心は猫をも殺す」 \n イギリスのことわざらしいにゃん。 怖いことわざもあるもんだにゃぁ～",
        	"猫の血液型は9割以上がA型らしいにゃん。 \n 私？ もちろんA型にゃ！",
        	"世の中には「猫カフェ」にゃるものがあるらしいにゃ。 \n にゃにそれ、行きたい！"
            };
    public static String generateSay() {
        Random rnd = new Random();
        int index = rnd.nextInt(SayList.length);
        return SayList[index];
    }

    // 猫種類からアイコンを決定
    private static String[] IconList = {
        	"563374c731b1b0e407093a9f",
        	"563374d831b1b0e408093a9f"
            };
    public static String determineIcon(String type) {
        // TODO: typeとの対応付けを適応する
        Random rnd = new Random();
        int index = rnd.nextInt(IconList.length);
        return IconList[index];
    }

    // にゃばたーを更新
    public static void updateNyavatar(DBObject nya) {
        ObjectId id = (ObjectId)nya.get("_id");
        dbColl.update(new BasicDBObject("_id", id), nya);
    }


    // instance member -----------------------------------------------------------

    private String nyavatarID;
    private String name;
    private String pictureID;
    private String iconID;
    private Date date;
    private Location location;
    private int like;
    private String lostCatID;

	// default constructor for jaxb
	public Nyavatar() {
        this.nyavatarID = "nullID";
        this.name = "nullName";
        this.pictureID = "nullID";
        this.iconID = "nullID";
        this.date = new Date(0); // 1970年1月1日 00:00:00 GMT
        this.location = new Location();
        this.like = 0;
        this.lostCatID = "nullID";
	}


    // getter / setter ---------------------------------------------------------
	@XmlElement(name="nyavatarID")
	public String getNyavatarID() {
		return nyavatarID;
	}
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	@XmlElement(name="pictureID")
	public String getPictureID() {
		return pictureID;
	}
	@XmlElement(name="iconID")
	public String getIconID() {
		return iconID;
	}
	@XmlElement(name="date")
	public Date getDate() {
		return date;
	}
	@XmlElement(name="location")
	public Location getLocation() {
		return location;
	}
	@XmlElement(name="like")
	public int getLike() {
		return like;
	}
	@XmlElement(name="lostCatID")
	public String getLostCatID() {
		return lostCatID;
	}

    public void setNyavatarID(String value) {
        this.nyavatarID = value != null ? value : "";
    }
    public void setName(String value) {
        this.name = value != null ? value : "";
    }
    public void setPictureID(String value) {
        this.pictureID = value != null ? value : "";
    }
    public void setIconID(String value) {
        this.iconID = value != null ? value : "";
    }
    public void setDate(Date value) {
        this.date = value;
    }
    public void setLocation(Location value) {
        this.location = value;
    }
    public void setLike(int value) {
        this.like = value;
    }
    public void setLostCatID(String value) {
        this.lostCatID = value != null ? value : "";
    }
}
