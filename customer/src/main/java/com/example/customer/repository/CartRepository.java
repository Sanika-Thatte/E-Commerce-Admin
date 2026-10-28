package com.example.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer.model.Cart;
import com.example.customer.model.User;

public interface CartRepository extends JpaRepository<Cart, Integer>{
	
	Optional<Cart> findByUser(User user);


}
