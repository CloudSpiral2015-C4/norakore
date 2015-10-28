package jp.kobe_u.cspiral.norakore.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="nyavatar")
public class Nyavatar {
    private String id;
    private String name;
    private Picture picture;  // ID
    private Icon icon;     // ID
    // private Advertisement advertisement; // ID
    // private Maigo maigo; // ID
    private Location location; // 多分Stringでなく専用型（モデル）を用意すべき
    private List<User> liker;

	// default constructor for jaxb
	public Nyavatar() {
        this.name = "None";
        this.id = "nya0001"; //このIDはどこで振るべき？
        this.picture = new Picture();
        this.icon = new Icon();
        this.location = new Location();
        this.liker = new ArrayList<User>();
	}

	@XmlElement(name="id")
	public String getId() {
		return id;
	}
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	@XmlElement(name="picture")
	public Picture getPicture() {
		return picture;
	}
	@XmlElement(name="icon")
	public Icon getIcon() {
		return icon;
	}
	@XmlElement(name="location")
	public Location getLocation() {
		return location;
	}
	@XmlElement(name="liker")
	public User[] getLiker() {
		return liker.toArray(new User[liker.size()]);
	}


    public void setId(String value) {
        this.id = value;
    }
    public void setName(String value) {
        this.name = value;
    }
    public void setPicture(Picture value) {
        this.picture = value;
    }
    public void setIcon(Icon value) {
        this.icon = value;
    }
    public void setLocation(Location value) {
        this.location = value;
    }
    public void setLiker(List<User> value) {
        this.liker = value;
    }

}
