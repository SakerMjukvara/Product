package se.jeli.model;

import javax.persistence.*;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

@Entity
public class User {

	@Id
  @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String pw;
	private String userHash;
	private String userSalt;
	
	public User(){
		
	}
	
	public User(String name, String pw) {
		this.name = name;
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getUserHash() {
		return userHash;
	}

	public void setUserHash(String userHash) {
		this.userHash = userHash;
	}

	public String getUserSalt() {
		return userSalt;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}
	
	
	
}

