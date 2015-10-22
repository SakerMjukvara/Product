package se.jeli.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

	private UserRepository repository;

	// private List<User> users;

	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	// Insert new user into DB
	public void insertUser(LoginUser user) {
		repository.save(user);

	}

	public List<LoginUser> findAll() {
		return (List<LoginUser>) repository.findAll();
	}

	// Fetch user from db. Get user object from DB OR look below for getting
	// only values
	public LoginUser findUser(LoginUser _user, UserRepository repository) {

		LoginUser user = (LoginUser) repository.findByName(_user.getName());

		return user;

	}

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
