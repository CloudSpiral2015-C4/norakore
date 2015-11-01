package jp.kobe_u.cspiral.norakore.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="location")
public class Location {
    private double lon;
    private double lat;

	// default constructor for jaxb
	public Location() {
        this.lon = 0;
        this.lat = 0;
	}
	public Location(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
	}

    public Location(DBObject dbo) {
        this.lon = (double)dbo.get("lon");
        this.lat = (double)dbo.get("lat");
    }

    public DBObject toDBObject() {
        DBObject dbo = new BasicDBObject();
        dbo.put("lon", this.lon);
        dbo.put("lat", this.lat);

        return dbo;
    }

	@XmlElement(name="lon")
	public double getLon() {
		return lon;
	}
	@XmlElement(name="lat")
	public double getLat() {
		return lat;
	}

    public void setLon(double value) {
        this.lon = value;
    }
    public void setLat(double value) {
        this.lat = value;
    }
}
