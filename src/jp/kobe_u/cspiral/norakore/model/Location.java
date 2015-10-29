package jp.kobe_u.cspiral.norakore.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="location")
public class Location {
    private double x;
    private double y;

	// default constructor for jaxb
	public Location() {
        this.x = 0;
        this.y = 0;
	}

    public Location(DBObject dbo) {
        this.x = (double)dbo.get("x");
        this.y = (double)dbo.get("y");
    }

    public DBObject toDBObject() {
        DBObject dbo = new BasicDBObject();
        dbo.put("x", this.x);
        dbo.put("y", this.y);

        return dbo;
    }

	@XmlElement(name="x")
	public double getX() {
		return x;
	}
	@XmlElement(name="y")
	public double getY() {
		return y;
	}

    public void setX(double value) {
        this.x = value;
    }
    public void setY(double value) {
        this.y = value;
    }
}
