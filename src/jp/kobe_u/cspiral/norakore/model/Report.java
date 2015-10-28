package jp.kobe_u.cspiral.norakore.model;

import java.util.ArrayList;
import java.util.List;
import jp.kobe_u.cspiral.norakore.model.Nyavatar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="report")
public class Report {
	private List<Nyavatar> nyavatar_list;

	// default constructor for jaxb
	public Report() {
		nyavatar_list = new ArrayList<Nyavatar>();
	}

	@XmlElement(name="list")
	public List<Nyavatar> getList() {
		return nyavatar_list;
	}

	public void setList(List<Nyavatar> value) {
		this.nyavatar_list = value;
	}

}
