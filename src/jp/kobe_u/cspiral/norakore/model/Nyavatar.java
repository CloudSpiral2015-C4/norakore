package jp.kobe_u.cspiral.norakore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.BasicDBList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="nyavatar")
public class Nyavatar {
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
        this.date = new Date();
        this.location = new Location();
        this.like = 0;
        this.lostCatID = "nullID";
	}

    public Nyavatar(DBObject dbo) {
        this.nyavatarID = (String)dbo.get("nyavatarID");
        this.name = (String)dbo.get("name");
        this.pictureID = (String)dbo.get("pictureID");
        this.iconID = (String)dbo.get("iconID");
        this.date = (Date)dbo.get("date");
        this.location = new Location((DBObject)dbo.get("location"));

        BasicDBList liker = (BasicDBList)dbo.get("likeUsers");
        this.like = liker.size();

        this.lostCatID = (String)dbo.get("lostCatID");
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
