package jp.kobe_u.cspiral.norakore.model;

import java.util.ArrayList;
import java.util.HashMap;
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
    	// グレー(0)
        	"564431ae2a82b0e4f491d12f",
        	"564431f02a82b0e4f591d12f",
        	"564431fd2a82b0e4f691d12f",
        	"564432092a82b0e4f791d12f",
        	"564432112a82b0e4f891d12f",
        	"5644321a2a82b0e4f991d12f",
        // 黒(1)
        	"5644322a2a82b0e4fa91d12f",
        	"5644323c2a82b0e4fb91d12f",
        	"564432472a82b0e4fc91d12f",
        	"564432502a82b0e4fd91d12f",
        	"564432582a82b0e4fe91d12f",
        	"564432632a82b0e4ff91d12f",
        // 茶(2)
        	"5644326f2a82b0e40092d12f",
        	"564432782a82b0e40192d12f",
        	"564432862a82b0e40292d12f",
        	"564432902a82b0e40392d12f",
        	"5644329a2a82b0e40492d12f",
        	"564432a42a82b0e40592d12f",
        // 茶白(3)
        	"564432b32a82b0e40692d12f",
        	"564432fe2a82b0e40792d12f",
        	"564433072a82b0e40892d12f",
        	"564433102a82b0e40992d12f",
        	"5644331b2a82b0e40a92d12f",
        	"564433272a82b0e40b92d12f",
        // 白(4)
        	"564433332a82b0e40c92d12f",
        	"5644333d2a82b0e40d92d12f",
        	"564433462a82b0e40e92d12f",
        	"5644334f2a82b0e40f92d12f",
        	"564433592a82b0e41092d12f",
        	"564433622a82b0e41192d12f",
        // 白グレ(5)
        	"5644336f2a82b0e41292d12f",
        	"5644337a2a82b0e41392d12f",
        	"564433802a82b0e41492d12f",
        	"564433882a82b0e41592d12f",
        	"564433942a82b0e41692d12f",
        	"564433a82a82b0e41792d12f",
        // 白黒(6)
        	"564433b62a82b0e41892d12f",
        	"564433c72a82b0e41992d12f",
        	"564433cf2a82b0e41a92d12f",
        	"564433db2a82b0e41b92d12f",
        	"564433e42a82b0e41c92d12f",
        	"564433ee2a82b0e41d92d12f",
            };
    public static String determineIcon(String type) {
    	// この猫なに猫？APIで返ってくる猫種類とIconListのtypeIndexとの対応Map
    	HashMap<String,Integer> map = new HashMap<String,Integer>();
    	map.put("abyssinian", 3);
    	map.put("Aegean_cat", 5);
    	map.put("alpine_lynx_cat", 4);
    	map.put("american_bobtail", 2);
    	map.put("american_curl", 5);
    	map.put("American_Keuda", 2);
    	map.put("American_Polydactyl_cat", 6);
    	map.put("American_Ringtail_cat", 6);
    	map.put("american_short_hair", 3);
    	map.put("american_wirehair", 5);
    	map.put("Arabian_Mau", 2);
    	map.put("Asian_cats", 0);
    	map.put("abyssinian", 3);
    	map.put("Australian_mist", 0);
    	map.put("Bahraini_Dilmun_Cat", 2);
    	map.put("Balinese_cat", 4);
    	map.put("Bambino_cat", 5);
    	map.put("Bengal_cat", 2);
    	map.put("Birman", 3);
    	map.put("bombay_cat", 1);
    	map.put("Bramble_cat", 5);
    	map.put("Brazilian_Shorthair", 6);
    	map.put("BritishShortHair", 4);
    	map.put("British_Longhair", 0);
    	map.put("Burmese", 2);
    	map.put("Chartreux", 0);
    	map.put("Cornish_Rex", 0);
    	map.put("Cymric", 2);
    	map.put("Devon_Rex", 2);
    	map.put("Egyptian_Mau", 0);
    	map.put("Exotic_Shorthair", 5);
    	map.put("HavanaBrown_cat", 1);
    	map.put("Himalayan_cat", 5);
    	map.put("Japanese_Bobtail", 6);
    	map.put("Korat", 0);
    	map.put("LaPerm", 2);
    	map.put("Mainecoon", 2);
    	map.put("Manx_cat", 1);
    	map.put("munchkin_cat", 2);
    	map.put("Norwegian_Forest_Cat", 2);
    	map.put("Ocicat", 2);
    	map.put("Oriental_cat", 2);
    	map.put("Persian_cat", 4);
    	map.put("PixieBob", 2);
    	map.put("Ragamuffin_cat", 6);
    	map.put("Ragdoll", 4);
    	map.put("RussianBlue", 0);
    	map.put("Scottish_Fold", 0);
    	map.put("Selkirk_Rex", 5);
    	map.put("Siamese", 6);
    	map.put("Siberian_cat", 5);
    	map.put("Singapura_cat", 5);
    	map.put("Snowshoe_cat", 6);
    	map.put("Somali_cat", 2);
    	map.put("Sphynx_cat", 4);
    	map.put("Tonkinese", 4);
    	map.put("Turkish_Angora", 4);
    	map.put("Turkish_Van", 4);
    	map.put("三毛猫", 3);
    	map.put("黒猫", 1);
    	map.put("白猫", 4);

    	Random rnd = new Random();
        int index = rnd.nextInt(IconList.length);

        // mapに存在する猫種類ならmap.containsKey(type) = true
    	if(map.containsKey(type)){
    		int typeIndex = rnd.nextInt(6);
    		index = map.get(type) * 6 + typeIndex;
    	}

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
