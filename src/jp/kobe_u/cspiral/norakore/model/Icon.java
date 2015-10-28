package jp.kobe_u.cspiral.norakore.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="icon")
public class Icon {
    private String id;

	// default constructor for jaxb
	public Icon() {
        this.id = "ico0001";
	}

	@XmlElement(name="id")
	public String getId() {
		return id;
	}

    public void setId(String value) {
        this.id = value;
    }
}
