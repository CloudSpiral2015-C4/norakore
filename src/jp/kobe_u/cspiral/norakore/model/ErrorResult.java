package jp.kobe_u.cspiral.norakore.model;

import java.util.ArrayList;
import java.util.List;
import jp.kobe_u.cspiral.norakore.model.Nyavatar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ErrorResult")
public class ErrorResult {
    private String message;

	// default constructor for jaxb
	public ErrorResult() {
        this.message = "error";
	}
	public ErrorResult(String message) {
        this.message = message;
	}

	@XmlElement(name="message")
	public String getMessage() {
		return message;
	}
	public void setMessage(String value) {
        this.message = value != null ? value : "error";
	}
}
