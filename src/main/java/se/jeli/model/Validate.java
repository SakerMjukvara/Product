package se.jeli.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validate {

	private UserService userService;

	@Autowired
	public Validate(UserService userService) {
		this.userService = userService;
	}

	/**
	 * This method validates that the enteredUserName and pw exists and matches.
	 * So that the pw belongs to the user.
	 * 
	 * 
	 * @param enteredUserName
	 * @param pwToCheck
	 * @return true if the username Exists and the hashed PW is equal to the
	 *         saved hashed PW. If one of those
	 */
	public boolean isAuthorized(String enteredUserName, String pwToCheck) {

		if (!userNameIsInDB(enteredUserName)) {
			return false;
		}

		boolean isPwCorrect = validatePassword(enteredUserName, pwToCheck);

		return isPwCorrect;
	}

	/**
	 * Method checks that the enteredUserName exists in the database.
	 * 
	 * @param enteredUserName
	 * @return true if the userName exists
	 */
	private boolean userNameIsInDB(String enteredUserName) {

		List<LoginUser> allUsers = userService.findAll();

		for (LoginUser userFromDb : allUsers) {
			if (userFromDb.getName().equals(enteredUserName)) {

				return true;
			}
		}

		return false;
	}

	/**
	 * Method checks that the enteredPW for the enteredUserName will get correct
	 * hash when it is hashed with the saved UserSalt
	 * 
	 * @param enteredUserName
	 * @param pwToCheck
	 * @return true if the pw can genereate same hash as in db. Otherwise false.
	 */
	private boolean validatePassword(String enteredUserName, String pwToCheck) {

		try {

			// Get salt from db and create hashed pw to check

			List<LoginUser> allUsers = userService.findAll();

			for (LoginUser userFromDb : allUsers) {
				if (userFromDb.getName().equals(enteredUserName)) {

					String hashedPW = Digester.hashString(pwToCheck + userFromDb.getUserSalt());

					if (userFromDb.getuserHashPw().equals(hashedPW)) {
						return true;
					}
				}
			}

			return false;

		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("något gick fel med hashning");
			return false;
		}
	}

}
