package jp.kobe_u.cspiral.norakore.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.*;

import org.bson.types.ObjectId;

import jp.kobe_u.cspiral.norakore.util.DBUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
public class User {
    // static member -----------------------------------------------------------
	private static DBCollection dbColl;
    static {
        dbColl = DBUtils.getInstance().getDb().getCollection("user");
    }

    // 指定されたidのユーザのDBObjectを取得
    public static DBObject getDBObject(String id) throws Exception{
        DBObject query = new BasicDBObject("_id", id);
        DBObject user = dbColl.findOne(query);
        if (user == null) throw new Exception(MessageFormat.format(
                "Specified user is not found. id={0}", id));
        return user;
    }

    // 指定ユーザのにゃばたーリストを取得
    public static BasicDBList getNyavatarList(DBObject user) throws Exception {
        BasicDBList list = (BasicDBList)user.get("nyavatarList");
        if (list == null) throw new Exception("user's nyavatarList is not found.");
        return list;
    }

    public static String getPassword(DBObject user) throws Exception {
        String pass = (String)user.get("password");
        if (pass == null) throw new Exception("user's password is not found on DB.");
        return pass;
    }

    public static BasicDBList addNyavatar(DBObject user, String nyavatarID) throws Exception {
        BasicDBList list = User.getNyavatarList(user);
        if (list.contains(nyavatarID)) throw new Exception("user already has the nyavatar.");
        list.add(nyavatarID);
        return list;
    }

    public static BasicDBList removeNyavatar(DBObject user, String nyavatarID) throws Exception {
    	BasicDBList list = User.getNyavatarList(user);
    	int index = list.indexOf(nyavatarID);
    	if(index == -1) throw new Exception("user has not the nyavatar.");
    	list.remove(index);
    	return list;
    }

    public static double addBonitos(DBObject user, double new_bonitos) throws Exception {
        Object bo = user.get("bonitos");
        if (bo == null) throw new Exception("user's bonitos doesn't exist on DB.");
        double bonitos = (Double)bo;
        bonitos += new_bonitos;
        user.put("bonitos", bonitos);
        return bonitos;
    }

    // ユーザを更新
    public static void updateUser(DBObject user) {
        String id = (String)user.get("_id");
        dbColl.update(new BasicDBObject("_id", id), user);
    }

    // instance member ---------------------------------------------------------
    private String id;
    private String name;
    private String password;
    private List<String> nyavatarList;
    private List<String> itemList;
    private Double bonitos;
    private String userType;

	// default constructor for jaxb
	public User() {
        this.name = "";
        this.id = "";
        this.password = "";
        this.nyavatarList = new ArrayList<String>();
        this.itemList = new ArrayList<String>();
        this.bonitos = 0.0;
        this.userType = "user";
	}

    public DBObject toDBObject() {
        DBObject dbo = new BasicDBObject();
        dbo.put("_id", this.id);
        dbo.put("name", this.name);
        dbo.put("password", this.password);
        dbo.put("nyavatarList", this.nyavatarList);
        dbo.put("itemList", this.itemList);
        dbo.put("bonitos", this.bonitos);
        dbo.put("userType", this.userType);

        return dbo;
    }

	@XmlElement(name="userID")
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
	public List<String> getNyavatarList(){
		return nyavatarList;
	}
	public List<String> getItemList(){
		return itemList;
	}
	public Double getBonitos(){
		return bonitos;
	}
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
	public void setBonitos(Double value){
		this.bonitos = value;
	}
	public void setUserType(String value) {
		this.userType = value != null ? value : "";
	}
}
