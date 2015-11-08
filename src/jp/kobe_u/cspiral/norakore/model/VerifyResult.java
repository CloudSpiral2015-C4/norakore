package jp.kobe_u.cspiral.norakore.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="VerifyResult")
public class VerifyResult {
    private String userID;
    private Boolean result;
    private String message;

	// default constructor for jaxb
	public VerifyResult() {
        this.userID = "nullID";
        this.result = false;
        this.message = "error";
	}

    public static VerifyResult ok(String userID) {
        VerifyResult res = new VerifyResult();
        res.userID = userID;
        res.result = true;
        res.message = "ログインに成功しました";
        return res;
    }
    public static VerifyResult UserNotFound(String userID) {
        VerifyResult res = new VerifyResult();
        res.userID = userID;
        res.result = false;
        res.message = "指定されたユーザは存在しません";
        return res;
    }
    public static VerifyResult InvalidPass(String userID) {
        VerifyResult res = new VerifyResult();
        res.userID = userID;
        res.result = false;
        res.message = "パスワードが一致しません";
        return res;
    }

	@XmlElement(name="userId")
	public String getUserID() {
		return userID;
	}
	@XmlElement(name="result")
	public Boolean getResult() {
		return result;
	}
	@XmlElement(name="message")
	public String getMessage() {
		return message;
	}
}
