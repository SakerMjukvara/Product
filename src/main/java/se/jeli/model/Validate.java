package se.jeli.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Validate {

	private static final String TESTNAME = "tester";
	private static final String TESTPW = "test";

	public boolean isAuthorized(String name, String pw){

		// If name = testname?
		// IF pw = testPW?

		MessageDigest digester;
		try {
			digester = MessageDigest.getInstance("SHA-256");
			byte[] enteredPw = digester.digest(pw.getBytes());
		} catch (NoSuchAlgorithmException e) {
			// TODO Error hantering
			e.printStackTrace();
		}

		//Convert byte[] to String
		
		return true;
	}

}
