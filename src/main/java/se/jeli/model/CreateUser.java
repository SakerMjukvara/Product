package se.jeli.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates the user to be stored in the database
 * 
 * @author Jesper Nee
 *
 */

@Component
public class CreateUser {

	private static final String ACCEPTED_SIGNS = "abcdefghijklmnopqrstuvwxyz1234567890";
	@Autowired
	private UserService userService;

	public boolean createUsr(String name, String pw) {
		boolean created = false;

		if (checkChars(name) & checkChars(pw) == true & checkUserName(name) == false) {
			// Create hashed pw here with salt
			LoginUser loginUser = new LoginUser(name, pw);
			String salted;
			try {
				salted = new String(createSalt(), "UTF-8");
				loginUser.setUserSalt(salted);
				String hashedPw = createHashedPw(pw, salted);
				loginUser.setuserHashPw(hashedPw);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				System.out.println("något gick fel med hashningen av lösenordet");
			}

			userService.insertUser(loginUser);
			created = true;
		}
		return created;

	}

	/**
	 * Checks that the characters in username and password (whitelist)
	 * @param text
	 * This is the name or password the user put in
	 * @return boolean
	 * True if OK and false if not OK
	 */
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
/**
 * Check that the choosen name is free to use (not in the database)
 * @param name
 * User choosen name
 * @return boolean
 * True if OK
 */
	private boolean checkUserName(String name) {
		boolean userExist = false;

		LoginUser findUser = userService.findUser(name);

		if (findUser != null) {
			return true;
		}
		// if (findUser.getName().length() > 0) {
		// userExist = true;
		// }
		// Send back text to web?????
		return userExist;
	}

	/**
	 * Create a random salt for each new user
	 * @return salt
	 * The created salt value
	 */
	private static byte[] createSalt() {
		final Random RANDOM = new SecureRandom();
		byte[] salt = new byte[32];
		RANDOM.nextBytes(salt);
		return salt;
	}

	/**
	 * Create the hashed password (with salt) to store in the database
	 * @param pw
	 * Password from the user
	 * @param salted
	 * This is the salt value
	 * @return hashedPW
	 * The hashed salt + password to be stored in the database
	 */
	private static String createHashedPw(String pw, String salted) {
		String hashedPW = null;
		String toHash = pw + salted;
		try {
			hashedPW = Digester.hashString(toHash);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("något har gått fel vid hashningen");
		}

		return hashedPW;
	}

}
