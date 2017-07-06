package model;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Clase POJO
 * @author alumne
 *
 */


public class Comments extends Table{

	private String MyUser;
	private String email;
	private String webpage;
	private Date datum;
	private String summary;
	private String comments;
	
	
	public Comments(){
		setId(-1);
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getMyUser() {
		return MyUser;
	}
	public void setMyUser(String myUser) {
		MyUser = myUser;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebpage() {
		return webpage;
	}
	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
}