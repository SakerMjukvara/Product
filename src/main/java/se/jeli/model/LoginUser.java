package se.jeli.model;

import javax.persistence.*;

@Entity
@Table(name="loginuser")
public class LoginUser {

	@Id
	@GeneratedValue()
	private long id;
	private String name;
	private String userHashPw;
	private String userSalt;

	public LoginUser() {

	}

	public LoginUser(String name, String userHashPw) {
		this.name = name;
		this.userHashPw = userHashPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getuserHashPw() {
		return userHashPw;
	}

	public void setPw(String userHashPw) {
		this.userHashPw = userHashPw;
	}

	public String getUserSalt() {
		return userSalt;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

}
