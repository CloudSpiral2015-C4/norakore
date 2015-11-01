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

    public static BasicDBList addNyavatar(DBObject user, String nyavatarID) throws Exception {
        BasicDBList list = User.getNyavatarList(user);
        list.add(nyavatarID);
        return list;
    }

    public static int addBonitos(DBObject user, int new_bonitos) throws Exception {
        Object bo = user.get("bonitos");
        if (bo == null) throw new Exception("user's bonitos doesn't exist on DB.");
        double bonitos_d = (Double)bo;
        int bonitos = (int)bonitos_d + 10;
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
	public Integer getBonitos(){
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
	public void setBonitos(Integer value){
		this.bonitos = value;
	}
	public void setUserType(String value) {
		this.userType = value != null ? value : "";
	}
}
