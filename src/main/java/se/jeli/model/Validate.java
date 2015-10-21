package se.jeli.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validate {

	private static final String TESTNAME = "tester";
	private static final String TESTPW = "test";
	//private static final String ACCEPTED_SIGNS = "abcdefghijklmnopqrstuvwxyz1234567890";
	private static final String testPwHash = initate(TESTPW);

	private UserService userService;

	@Autowired
	public Validate(UserService userService) {
		this.userService = userService;
	}

	public void extracted() {
		LoginUser user = new LoginUser(TESTNAME, testPwHash);
		user.setUserSalt("2");
		userService.insertUser(user);
		System.out.println("USER: " + user.getName());

		LoginUser user2 = new LoginUser("jesper", initate("pw"));
		user2.setUserSalt("2");
		userService.insertUser(user2);
		System.out.println("USER Jesper " + user2.getName());
		userService.findAll().forEach(u -> System.out.println(user.getName()));
	}

	public boolean isAuthorized(String enteredUserName, String pwToCheck) {

		if (!userNameIsInDB(enteredUserName)) {
			System.out.println(enteredUserName);
			return false;
		}

		boolean isPwCorrect = validatePassword(pwToCheck);

		return isPwCorrect;
	}

	private boolean userNameIsInDB(String enteredUserName) {

		List<LoginUser> allUsers = userService.findAll();
		
		for (LoginUser userFromDb : allUsers) {
			if (userFromDb.getName().equals(enteredUserName)) {
				return true;
			}
		}

		return false;
	}

	private boolean validatePassword(String pwToCheck) {

		try {
			String hashedPW = hashString(pwToCheck);

			System.out.println(pwToCheck + " " + hashedPW);

			if (testPwHash.equals(hashedPW)) {
				return true;
			}

			return false;

		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			// TODO: Errorhandling
			return false;
		}
	}

	private static String hashString(String pwToCheck) throws UnsupportedEncodingException, NoSuchAlgorithmException {

		MessageDigest digester = MessageDigest.getInstance("SHA-256");
		digester.reset();
		digester.update(pwToCheck.getBytes("UTF-8"));

		byte[] enteredPwHash = digester.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < enteredPwHash.length; ++i) {
			sb.append(Integer.toHexString((enteredPwHash[i] & 0xFF) | 0x100).substring(1, 3));
		}

		return sb.toString();
	}

	/**
	 * Method only for passing first step with hardcoded username & pw
	 * 
	 * @param pwString
	 * @return
	 */
	private static String initate(String pwString) {
		try {
			return hashString(pwString);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "fattas";
	}

	

}
