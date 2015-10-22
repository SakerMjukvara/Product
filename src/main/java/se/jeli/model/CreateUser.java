package se.jeli.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CreateUser {

	private static final String ACCEPTED_SIGNS = "abcdefghijklmnopqrstuvwxyz1234567890";
	@Autowired
	 private UserService userService;

	public boolean createUsr(String name, String pw) {
		boolean created = false;

		if (checkChars(name) & checkChars(pw) == true & checkUserName(name) == true) {
			// Create hashed pw here with salt
			LoginUser loginUser = new LoginUser(name, pw);
			String salted;
			try {
				salted = new String(createSalt(), "UTF-8");
				loginUser.setUserSalt(salted);
				String hashedPw = createHashedPw(pw, salted);
				loginUser.setuserHashPw(hashedPw);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			userService.insertUser(loginUser);
			created = true;
		}
		return created;

	}

	private boolean checkChars(String text) {
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

	private boolean checkUserName(String name) {
		boolean userExist = false;

		if (userService.findUser(name).getName().length() > 0) {
			userExist = true;
		}
		// Send back text to web?????
		return userExist;
	}

	private static byte[] createSalt() {
		final Random RANDOM = new SecureRandom();
		byte[] salt = new byte[32];
		RANDOM.nextBytes(salt);
		return salt;
	}

	private static String createHashedPw(String pw, String salted) {
		String hashedPW = null;
		String toHash = salted + pw;
		try {
			hashedPW = Digester.hashString(toHash);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hashedPW;
	}

}
