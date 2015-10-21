package se.jeli.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;





@Component
public class Validate {

	private static final String TESTNAME = "tester";
	private static final String TESTPW = "test";

	private static final String testPwHash = initate(TESTPW);

	public boolean isAuthorized(String enteredUserName, String pwToCheck) {

		if (!userNameIsInDB(enteredUserName)) {
			return false;
		}

		boolean isPwCorrect = validatePassword(pwToCheck);

		return isPwCorrect;
	}

	
		

	
	private boolean userNameIsInDB(String enteredUserName) {
		// TODO: Create connection to db here
		
		return TESTNAME.equals(enteredUserName);
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
