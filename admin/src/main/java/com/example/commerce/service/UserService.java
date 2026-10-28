package com.example.commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.commerce.model.User;
import com.example.commerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

//	Signup
	public User findUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User register(User user) {
		return userRepository.save(user);
		
	}

//	Login
	public User getUsernameAndPassword(String username, String password) {
		return userRepository.findByUsernameAndPassword(username,password);
	}
	
	

}
