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

@XmlRootElement(name="nyavatar_detail")
public class NyavatarDetail {
    private String id;
    private String name;
    private String picture;
    private String icon;
    private String type;
    private double like;
    private Date last_date;
    private Location location;
    private List<String> like_user;

	// default constructor for jaxb
	public NyavatarDetail() {
        this.name = "";
        this.id = "";
        this.picture = "";
        this.icon = "";
        this.type = "";
        this.like = 0;
        this.last_date = new Date();
        this.location = new Location();
        this.like_user = new ArrayList<String>();
	}

    // アップロード時など，欠落しているパラメータを決定する
    public void determineParams() {
        Calendar cal = Calendar.getInstance();
        this.last_date = cal.getTime();

        if (this.name.equals("")) {
            // ランダム？
            this.name = "猫";
        }

        if (this.type.equals("")) {
            // pictureから何猫で決定
            this.type = "type";
        }

        if (this.icon.equals("")) {
            // typeから決定するかpictureから類似度するかで決定
            this.icon = "icon_id";
        }
    }

    public DBObject toDBObject() {
        DBObject dbo = new BasicDBObject();
        dbo.put("name", this.name);
        dbo.put("picture", this.picture);
        dbo.put("icon", this.icon);
        dbo.put("type", this.type);
        dbo.put("like", this.like);
        dbo.put("last_date", this.last_date);
        dbo.put("location", this.location.toDBObject());
        BasicDBList list = new BasicDBList();
        for (String user: this.like_user) {
            list.add(user);
        }
        dbo.put("like_user", this.like_user);

        return dbo;
    }

    // getter / setter ---------------------------------------------------------
	@XmlElement(name="id")
	public String getId() {
		return id;
	}
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	@XmlElement(name="picture")
	public String getPicture() {
		return picture;
	}
	@XmlElement(name="icon")
	public String getIcon() {
		return icon;
	}
	@XmlElement(name="type")
	public String getType() {
		return type;
	}
	@XmlElement(name="like")
	public double getLike() {
		return like;
	}
	@XmlElement(name="last_date")
	public Date getLastDate() {
		return last_date;
	}
	@XmlElement(name="location")
	public Location getLocation() {
		return location;
	}
	@XmlElement(name="like_user")
	public String[] getLikeUser() {
		return like_user.toArray(new String[like_user.size()]);
	}

    public void setId(String value) {
        this.id = value != null ? value : "";
    }
    public void setName(String value) {
        this.name = value != null ? value : "";
    }
    public void setPicture(String value) {
        this.picture = value != null ? value : "";
    }
    public void setIcon(String value) {
        this.icon = value != null ? value : "";
    }
    public void setType(String value) {
        this.type = value != null ? value : "";
    }
    public void setLike(double value) {
        this.like = value;
    }
    public void setLastDate(Date value) {
        this.last_date = value;
    }
    public void setLocation(Location value) {
        this.location = value;
    }
    public void setLikeUser(List<String> value) {
        this.like_user = value;
    }

}
