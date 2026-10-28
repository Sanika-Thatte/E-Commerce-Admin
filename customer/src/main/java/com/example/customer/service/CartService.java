package com.example.customer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.model.Cart;
import com.example.customer.model.CartItem;
import com.example.customer.model.Order;
import com.example.customer.model.OrderItem;
import com.example.customer.model.Product;
import com.example.customer.model.User;
import com.example.customer.repository.CartItemRepository;
import com.example.customer.repository.CartRepository;
import com.example.customer.repository.OrderRepository;
import com.example.customer.repository.ProductRepository;
import com.example.customer.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

	@Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // ================================
    // 1️⃣ Order the entire cart
    // ================================
    @Transactional
    public void orderCart(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        List<CartItem> items = cart.getItems();

        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderdate(LocalDateTime.now());
        order.setStatus("PENDING");

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem item : items) {

            Product product = item.getProduct();
            int qty = item.getQuantity();

            if (product.getQuantity() < qty) {
                throw new RuntimeException("Insufficient stock for " + product.getName());
            }

            // Reduce stock
            product.setQuantity(product.getQuantity() - qty);
            productRepository.save(product);

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(qty);

            orderItems.add(orderItem);

            // Calculate total
            total += product.getPrice() * qty;
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);   // ✅ added
        orderRepository.save(order);

        // Clear cart
        cartItemRepository.deleteAll(items);
    }

    // ================================
    // 2️⃣ Order a single cart item
    // ================================
    @Transactional
    public void orderSingleCartItem(Integer cartItemId) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Product product = item.getProduct();
        int qty = item.getQuantity();

        if (product.getQuantity() < qty) {
            throw new RuntimeException("Insufficient stock for " + product.getName());
        }

        Order order = new Order();
        order.setUser(item.getCart().getUser());
        order.setOrderdate(LocalDateTime.now());
        order.setStatus("PENDING");

        // Create order item
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(qty);

        order.setItems(List.of(orderItem));

        // Reduce stock
        product.setQuantity(product.getQuantity() - qty);
        productRepository.save(product);

        // Total
        order.setTotalAmount(product.getPrice() * qty);

        orderRepository.save(order);

        // Remove item from cart
        cartItemRepository.delete(item);
    }
    
    
    public List<CartItem> getCartItems(String username) {

        // 🔹 Get user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔹 Get cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // 🔹 Return items
        return cart.getItems();
    }
}
