package jp.kobe_u.cspiral.norakore.model;

import java.util.ArrayList;
import java.util.List;
import jp.kobe_u.cspiral.norakore.model.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="picture")
public class Picture {
    private String id;

	// default constructor for jaxb
	public Picture() {
        this.id = "pic0001";
	}

	@XmlElement(name="id")
	public String getId() {
		return id;
	}

    public void setId(String value) {
        this.id = value;
    }
}
