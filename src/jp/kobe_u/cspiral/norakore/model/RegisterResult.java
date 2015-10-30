package jp.kobe_u.cspiral.norakore.model;

import java.util.ArrayList;
import java.util.List;
import jp.kobe_u.cspiral.norakore.model.Nyavatar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RegisterResult")
public class RegisterResult {
    private String nyavatarID;
    private int bonitos;

	// default constructor for jaxb
	public RegisterResult() {
        this.nyavatarID = "nullID";
        this.bonitos = 0;
	}

	@XmlElement(name="nyavataID")
	public String getNyavataID() {
		return nyavatarID;
	}
	@XmlElement(name="bonitos")
	public int getBonitos() {
		return bonitos;
	}

	public void setNyavatarID(String value) {
        this.nyavatarID = value != null ? value : "nullID";
	}
	public void setBonitos(int value) {
        this.bonitos = value;
	}
}
