package jp.kobe_u.cspiral.norakore.model;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
public class User {
    private String id;
    private String name;
    private String password;
    private List<String> nyavatarList;
    private List<String> itemList;
    private Integer bonitos;
    private String userType;

	// default constructor for jaxb
	public User() {
        this.name = "";
        this.id = "";
        this.password = "";
        this.nyavatarList = new ArrayList<String>();
        this.itemList = new ArrayList<String>();
        this.bonitos = 0;
        this.userType = "user";
	}

    public DBObject toDBObject() {
        DBObject dbo = new BasicDBObject();
        dbo.put("name", this.name);
        dbo.put("password", this.password);

        return dbo;
    }

	@XmlElement(name="id")
	public String getId() {
		return id;
	}
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	// @XmlElement(name="password") これはAPI公開してはいけない
	public String getPassword() {
		return password;
	}
	@XmlElement(name="nyavatarList")
	public List<String> getNyavatarList(){
		return nyavatarList;
	}
	@XmlElement(name="itemList")
	public List<String> getItemList(){
		return itemList;
	}
	@XmlElement(name="bonitos")
	public Integer getBonitos(){
		return bonitos;
	}
	//@XmlElement(name="userType") API公開はしない
	public String getUserType() {
		return userType;
	}

    public void setId(String value) {
        this.id = value != null ? value : "";
    }
    public void setName(String value) {
        this.name = value != null ? value : "";
    }
    public void setPassword(String value) {
        this.password = value != null ? value : "";
    }
    public void setNyavatarList(List<String> value){
		this.nyavatarList = value;
	}
    public void setitemList(List<String> value){
		this.itemList = value;
	}
	public void setBonitos(Integer value){
		this.bonitos = value;
	}
	public void setUserType(String value) {
		this.userType = value != null ? value : "";
	}
}
