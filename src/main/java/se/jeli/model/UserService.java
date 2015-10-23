package se.jeli.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handles database queries
 * 
 * @author Jesper Nee
 *
 */

@Component
public class UserService {

	private UserRepository repository;

	
	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	/**
	 * Insert new user into DB
	 * @param user object
	 * 
	 */
	
	public void insertUser(LoginUser user) {
		repository.save(user);

	}
	
	/**
	 * Fetch all users
	 * @return user object
	 */

	public List<LoginUser> findAll() {
		return (List<LoginUser>) repository.findAll();
	}

	/**
	 * Find the user by name
	 * 
	 * @param userName
	 * 
	 * @return LoginUser object
	 */
	
	public LoginUser findUser(String userName) {

		List<LoginUser> findByName = repository.findByName(userName);

		if (findByName.isEmpty()) {
			return null;
		}

		return findByName.get(0);

	}

	// Fetch hashed pw for a specific user
	public String findHashPw(LoginUser _user, UserRepository repository) {

		LoginUser user = (LoginUser) repository.findByName(_user.getName());

		return user.getuserHashPw();

	}

}
