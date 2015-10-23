package se.jeli.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * This is where the data from the database is stored for further manipulation in the application.
 * 
 * @author Jesper Nee
 *
 */

public interface UserRepository extends CrudRepository<LoginUser, Long> {

	List<LoginUser> findByName(String name);
}