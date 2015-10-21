package se.jeli.model;




public class UserService {

	//private List<User> users;
	
	//Insert new user into DB
	public void insertUser(User _user, UserRepository repository){
		repository.save(_user);
		
	}
	
	
	
	//Fetch user from db. Get user object from DB OR look below for getting only values
	public User findUser(User _user, UserRepository repository) {
		
		
		User user = (User) repository.findByName(_user.getName());
			
			

		return user;
	
		
	}
	
	
	//Fetch hashed pw for a specific user
	public String findHashPw(User _user, UserRepository repository) {
	
	
		User user = (User) repository.findByName(_user.getName());
			
			

		return user.getuserHashPw();
	
		
	}
	
	
}
