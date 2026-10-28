package com.example.customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.customer.model.User;
import com.example.customer.repository.UserRepository;
import com.example.customer.model.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

//	Signup
	public Optional<User> findUsername(String username) {
	    return userRepository.findByUsername(username); // returns Optional<User>
	}
	public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

//	Login
	public User getUsernameAndPassword(String username, String password) {
		return userRepository.findByUsernameAndPassword(username,password);
	}
	
	

}
