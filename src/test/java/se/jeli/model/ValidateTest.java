package se.jeli.model;

import java.security.NoSuchAlgorithmException;

public class ValidateTest {

	public static void main(String[] args) throws NoSuchAlgorithmException {

		Validate validate = new Validate();
		
		validate.isAuthorized("tester", "test");
		
	}

}
