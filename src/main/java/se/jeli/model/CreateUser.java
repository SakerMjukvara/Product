package se.jeli.model;

import org.springframework.beans.factory.annotation.Autowired;

public class CreateUser {
	
private static final String ACCEPTED_SIGNS = "abcdefghijklmnopqrstuvwxyz1234567890";
@Autowired
private UserService userService; 
	
	
	public void createUsr(String name, String pw) {
		
		LoginUser loginUser = new LoginUser(name, pw);
		
		if (checkChars(name) & checkChars(pw)== true){
			userService.insertUser(loginUser);
		}
		
	}
	
	private boolean checkChars(String text){
		for (int i = 0; i < ACCEPTED_SIGNS.toCharArray().length; i++) {

		}

		char[] charArray = text.toCharArray();
		for (Character c : charArray) {
			if (!Character.isAlphabetic(c)) {
				if (!Character.isDigit(c)) {
					return false;
				}
			}
		}
		
		return true;
		
		
	}

}
