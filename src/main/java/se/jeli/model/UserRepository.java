package se.jeli.model;

import java.util.List;

import org.springframework.data.*;


public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByLastName(String lastName);
}