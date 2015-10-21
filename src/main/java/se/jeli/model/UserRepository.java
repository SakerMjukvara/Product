package se.jeli.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<LoginUser, Long> {

	List<LoginUser> findByName(String name);
}