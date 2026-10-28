package com.example.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.customer.model.Order;
import com.example.customer.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	
	 List<Order> findByUser(User user);
	 

}
