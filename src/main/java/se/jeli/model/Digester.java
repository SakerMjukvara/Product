package se.jeli.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Create the hashed salt + password
 * 
 * @author Lina, Jesper Nee
 *
 */

public class Digester {

	/**
	 * Uses MessageDigest to generate a hash for the pw.
	 * 
	 * @param pwToCheck
	 * @return the hashed pw
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String hashString(String pwToCheck) throws UnsupportedEncodingException, NoSuchAlgorithmException {

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
}