package com.example.customer.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.customer.model.Order;
import com.example.customer.model.OrderItem;
import com.example.customer.model.Product;
import com.example.customer.model.User;
import com.example.customer.repository.OrderRepository;
import com.example.customer.repository.ProductRepository;
import com.example.customer.repository.UserRepository;
	
@Service
public class OrderService {

	    @Autowired
	    private ProductRepository productRepository;

	    @Autowired
	    private OrderRepository orderRepository;

	    @Autowired
	    private UserRepository userRepository;

	    // ============================
	    // 1️⃣ Place Order (Product Page)
	    // ============================
	    
	    public void placeOrder(Integer productId, int quantity) {

	        // ✅ Get logged-in user
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        String username = auth.getName();

	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        // ✅ Get product
	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found"));

	        // ✅ Stock check
	        if (product.getQuantity() < quantity) {
	            throw new RuntimeException("Insufficient stock!");
	        }

	        // ✅ Reduce stock
	        product.setQuantity(product.getQuantity() - quantity);
	        productRepository.save(product);

	        // ============================
	        // Create Order
	        // ============================
	        Order order = new Order();
	        order.setUser(user);
	        order.setOrderdate(LocalDateTime.now());
	        order.setStatus("PENDING");

	        // ============================
	        // Create OrderItem
	        // ============================
	        OrderItem item = new OrderItem();
	        item.setOrder(order);
	        item.setProduct(product);
	        item.setQuantity(quantity);

	        order.setItems(List.of(item));

	        // ✅ Total amount
	        order.setTotalAmount(product.getPrice() * quantity);

	        // ✅ Save order (cascade will save items)
	        orderRepository.save(order);
	    }

	    // ============================
	    // 2️⃣ Get Orders for User
	    // ============================
	   
	    public List<Order> getOrdersForUser(String username) {

	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        return orderRepository.findByUser(user);
	    }
	}
