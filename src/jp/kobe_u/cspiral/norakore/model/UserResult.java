package jp.kobe_u.cspiral.norakore.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="UserResult")
public class UserResult {

	private String userID;
    private String name;
    private int bonitos;
    private List<String> iconidList;
    private List<String> itemidList;

	// default constructor for jaxb
	public UserResult() {
        this.userID = "nullID";
        this.name = "nullName";
        this.bonitos = 0;
        this.iconidList = new ArrayList<String>();
        this.itemidList = new ArrayList<String>();
	}

	@XmlElement(name="userID")
	public String getUserID() {
		return userID;
	}
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	@XmlElement(name="bonitos")
	public int getBonitos() {
		return bonitos;
	}
	@XmlElement(name="iconidList")
	public List<String> getIconIDList() {
		return iconidList;
	}
	@XmlElement(name="itemidList")
	public List<String> getItemIDList() {
		return itemidList;
	}

	public void setUserID(String value) {
        this.userID = value != null ? value : "nullID";
	}
	public void setName(String value) {
        this.name = value != null ? value : "nullName";
	}
	public void setBonitos(int value) {
        this.bonitos = value;
	}
	public void setIconIDList(List<String> value){
		this.iconidList = value;
	}
	public void addIconIDList(String value){
		this.iconidList.add(value);
	}
	public void setItemIDList(List<String> value){
		this.itemidList = value;
	}
	public void addItemIDList(String value){
		this.itemidList.add(value);
	}

}
