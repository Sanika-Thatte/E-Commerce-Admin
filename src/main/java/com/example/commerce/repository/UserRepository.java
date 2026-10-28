package com.example.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.commerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

//	SignUp
	User findByUsername(String username);

    User save(User user);

//  Login
	User findByUsernameAndPassword(String username, String password);
		

	
	
	

}
