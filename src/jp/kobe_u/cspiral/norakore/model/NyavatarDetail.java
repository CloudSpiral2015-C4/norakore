package jp.kobe_u.cspiral.norakore.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

import jp.kobe_u.cspiral.norakore.OuterAPI;

import com.mongodb.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

@XmlRootElement(name="NyavatarDetail")
public class NyavatarDetail {
    private String nyavatarID;
    private String name;
    private String pictureID;
    private String iconID;
    private String type;
    private Location location;
    private String advertisingID;
    private String lostCatID;
    private Date date;
    private List<String> likeUserList; // no setter, has addUser
    private String say;
    private Boolean isLiked; // no setter

	// default constructor for jaxb
	public NyavatarDetail() {
        // APIでの初期化漏れを分かりやすくするため，明らかにinvalidな値で初期化
        this.nyavatarID = "nullID";
        this.name = "nullName";
        this.pictureID = "nullID";
        this.iconID = "nullID";
        this.type = "nullType";
        this.location = new Location();
        this.advertisingID = "nullID";
        this.lostCatID = "nullID";
        this.date = new Date(0); // 1970年1月1日 00:00:00 GMT
        this.likeUserList = new ArrayList<String>();
        this.say = "nullSay";
        this.isLiked = false;
	}

    // アップロード時など，欠落しているパラメータを決定する
    public void determineParams(String UserID) {
        if (this.name.equals("")) {
            // ランダム？
            this.name = "猫";
        }

        if (this.type.equals("不明")) {
            this.type = OuterAPI.getNanineko(this.pictureID);
        }

        if (this.iconID.equals("")) {
            // TODO: typeから決定するかpictureから類似度するかで決定
            this.iconID = "icon_id";
        }

        // TODO: useridからisLikeをセット
        this.isLiked = false;
        for (String user: this.likeUserList) {
            if(user.equals(UserID)){
            	this.isLiked = true;
            }
        }

    }

    public DBObject toDBObject() {
        DBObject dbo = new BasicDBObject();
        dbo.put("name", this.name);
        dbo.put("pictureID", this.pictureID);
        dbo.put("iconID", this.iconID);
        dbo.put("type", this.type);
        dbo.put("location", this.location.toDBObject());
        dbo.put("advertisingID", this.advertisingID);
        dbo.put("lostCatID", this.lostCatID);
        dbo.put("date", this.date);
        dbo.put("say", this.say);
        BasicDBList list = new BasicDBList();
        for (String user: this.likeUserList) {
            list.add(user);
        }
        dbo.put("likeUserList", this.likeUserList); // TODO: 必要？

        return dbo;
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
	@XmlElement(name="type")
	public String getType() {
		return type;
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
	public int getLike() { // no param
		return likeUserList.size();
	}
	@XmlElement(name="isLiked")
	public Boolean getIsLiked() { // no param
		return isLiked;
	}
	@XmlElement(name="say")
	public String getSay() { // no param
		return say;
	}
	@XmlElement(name="lostCatID")
	public String getLostCatID() { // no param
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
    public void setType(String value) {
        this.type = value != null ? value : "";
    }
    public void setDate(Date value) {
        this.date = value;
    }
    public void setLocation(Location value) {
        this.location = value;
    }
    public void setLikeUsers(List<String> value) {
        this.likeUserList = value;
    }

    public void addLikeUser(String UserID) {
        this.likeUserList.add(UserID);
    }

    public void setSay(String value) {
        this.say = value != null ? value : "";
    }

    public void setLostCatID(String value) {
        this.lostCatID = value != null ? value : "";
    }
}
