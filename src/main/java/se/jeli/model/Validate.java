package se.jeli.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validate {

//	private static final String TESTNAME = "tester";
//	private static final String TESTPW = "test";
//	private static final String testPwHash = initate(TESTPW);
	private UserService userService;

	@Autowired
	public Validate(UserService userService) {
		this.userService = userService;
	}

	public boolean isAuthorized(String enteredUserName, String pwToCheck) {

		if (!userNameIsInDB(enteredUserName)) {
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

			// Get salt from db and create hashed pw to check

			List<LoginUser> allUsers = userService.findAll();

			for (LoginUser userFromDb : allUsers) {
				if (userFromDb.getName().equals(enteredUserName)) {
					
					String hashedPW = Digester.hashString(pwToCheck + userFromDb.getUserSalt());

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

//	/**
//	 * Method only for passing first step with hardcoded username & pw
//	 * 
//	 * @param pwString
//	 * @return
//	 */
//	private static String initate(String pwString) {
//		try {
//			return Digester.hashString(pwString);
//		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return "fattas";
//	}

}
