package se.jeli.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validate {

	private static final String TESTNAME = "tester";
	private static final String TESTPW = "test";
	// private static final String ACCEPTED_SIGNS =
	// "abcdefghijklmnopqrstuvwxyz1234567890";
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

		boolean isPwCorrect = validatePassword(enteredUserName, pwToCheck);

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

	private boolean validatePassword(String enteredUserName, String pwToCheck) {

		try {

			String hashedPW = Digester.hashString(pwToCheck);

			System.out.println(pwToCheck + " " + hashedPW);

			List<LoginUser> allUsers = userService.findAll();

			for (LoginUser userFromDb : allUsers) {
				if (userFromDb.getName().equals(enteredUserName)) {
					System.out.println("hittat användaren");
					System.out.println(userFromDb.getuserHashPw());
					if (userFromDb.getuserHashPw().equals(hashedPW)) {
						System.out.println("hittat lösenordet");
						return true;
					}
				}
			}

			return false;

		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();

			// TODO: Errorhandling
			return false;
		}
	}

	/**
	 * Method only for passing first step with hardcoded username & pw
	 * 
	 * @param pwString
	 * @return
	 */
	private static String initate(String pwString) {
		try {
			return Digester.hashString(pwString);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "fattas";
	}

}
