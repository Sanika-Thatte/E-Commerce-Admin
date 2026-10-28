package com.example.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer.model.Cart;
import com.example.customer.model.CartItem;
import com.example.customer.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
	
	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

}
