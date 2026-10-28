package com.example.customer.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.customer.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

//	SignUp
	Optional<User> findByUsername(String username);

    User save(User user);

//  Login
	User findByUsernameAndPassword(String username, String password);
	
	
	
	
		

	
	
	

}
