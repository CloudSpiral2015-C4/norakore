package jp.kobe_u.cspiral.norakore.model;

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

	// default constructor for jaxb
	public User() {
        this.name = "";
        this.id = "";
        this.password = "";
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

    public void setId(String value) {
        this.id = value;
    }
    public void setName(String value) {
        this.name = value;
    }
    public void setPassword(String value) {
        this.password = value;
    }
}
