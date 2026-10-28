package com.example.customer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.customer.model.Cart;
import com.example.customer.model.CartItem;
import com.example.customer.model.Order;
import com.example.customer.model.OrderItem;
import com.example.customer.model.Product;
import com.example.customer.model.User;
import com.example.customer.repository.ProductRepository;
import com.example.customer.repository.UserRepository;
import com.example.customer.repository.OrderRepository;
import com.example.customer.repository.CartItemRepository;
import com.example.customer.repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}
	

	public List<Product> findByProductIdOrProductNameOrProductCategory(Integer productid, String productname,String productcategory) 
	{
	     return productRepository.findByProductIdOrNameOrCategory_Name(productid,productname, productcategory);
	}


	public Product add(Product product) 
	{
		return productRepository.save(product);
	}


	public Product viewProduct(int productId) 
	{
		return productRepository.findByProductId(productId);
	}


	@Transactional
	public void delete(int productId) {
	    productRepository.deleteByProductId(productId);  
	}
	
	public List<Product> getTop6Products() {
        return productRepository.findTop6ByOrderByProductIdDesc();
    }


	 // Add to Cart
	@Transactional
	public void addToCart(Integer productId, int quantity, String username) {
	    // Fetch User
	    User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // Fetch Product
	    Product product = productRepository.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    // Fetch or create Cart
	    Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
	        Cart newCart = new Cart();
	        newCart.setUser(user);
	        newCart.setItems(new ArrayList<>());
	        return cartRepository.save(newCart);
	    });

	    // Check if item exists in cart using a simple loop (avoid streams)
	    boolean found = false;
	    for (CartItem item : cart.getItems()) {
	    	if (item.getProduct().getProductId() == productId) {
	    	    item.setQuantity(item.getQuantity() + quantity);
	    	    found = true;
	    	    break;
	    	}
	    }

	    // If not found, create new cart item
	    if (!found) {
	        CartItem newItem = new CartItem();
	        newItem.setCart(cart);
	        newItem.setProduct(product);
	        newItem.setQuantity(quantity);
	        cart.getItems().add(newItem);
	    }

	    // Save the cart (cascade will save items)
	    cartRepository.save(cart);
	}
	
//	Order whole cart
	@Transactional
	public void orderCart(Integer userId) {

	    // ✅ Fetch User
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // ✅ Fetch Cart
	    Cart cart = cartRepository.findByUser(user)
	            .orElseThrow(() -> new RuntimeException("Cart is empty"));

	    if (cart.getItems() == null || cart.getItems().isEmpty()) {
	        throw new RuntimeException("Cart is empty");
	    }

	    // ✅ Create Order
	    Order order = new Order();
	    order.setUser(user);
	    order.setOrderdate(LocalDateTime.now());

	    List<OrderItem> orderItems = new ArrayList<>();

	    // 🔁 Process each cart item
	    for (CartItem item : cart.getItems()) {

	        int qty = item.getQuantity();
	        Product product = item.getProduct();

	        // ❗ Stock check
	        if (product.getQuantity() < qty) {
	            throw new RuntimeException("Insufficient stock for " + product.getName());
	        }

	        // ✅ Reduce stock
	        product.setQuantity(product.getQuantity() - qty);
	        productRepository.save(product); // 🔥 important

	        // ✅ Create OrderItem
	        OrderItem orderItem = new OrderItem();
	        orderItem.setOrder(order);
	        orderItem.setProduct(product);
	        orderItem.setQuantity(qty); // since you renamed

	        orderItems.add(orderItem);
	    }

	    order.setItems(orderItems);

	    // ✅ Save Order (cascade will save items)
	    orderRepository.save(order);

	    // ✅ Clear cart from DB
	    cartItemRepository.deleteAll(cart.getItems());
	}
	
	
//	Order single item from cart
	@Transactional
	public void orderSingleCartItem(Integer cartItemId) {

	    CartItem item = cartItemRepository.findById(cartItemId)
	            .orElseThrow(() -> new RuntimeException("Item not found"));

	    Product product = item.getProduct();
	    int qty = item.getQuantity();

	    if (product.getQuantity() < qty) {
	        throw new RuntimeException("Insufficient stock");
	    }

	    // Create order
	    Order order = new Order();
	    order.setUser(item.getCart().getUser());
	    order.setOrderdate(LocalDateTime.now());

	    OrderItem orderItem = new OrderItem();
	    orderItem.setOrder(order);
	    orderItem.setProduct(product);
	    orderItem.setQuantity(qty);

	    order.setItems(List.of(orderItem));

	    product.setQuantity(product.getQuantity() - qty);
	    productRepository.save(product);

	    orderRepository.save(order);

	    // remove only that item
	    cartItemRepository.delete(item);
	}
	
	
//	Order from view product page
	@Transactional
	public void orderProduct(Integer productId, int quantity, String username) {

	    User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    Product product = productRepository.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    if (product.getQuantity() < quantity) {
	        throw new RuntimeException("Insufficient stock");
	    }

	    Order order = new Order();
	    order.setUser(user);
	    order.setOrderdate(LocalDateTime.now());

	    OrderItem item = new OrderItem();
	    item.setOrder(order);
	    item.setProduct(product);
	    item.setQuantity(quantity);

	    order.setItems(List.of(item));

	    product.setQuantity(product.getQuantity() - quantity);
	    productRepository.save(product);

	    orderRepository.save(order);
	}


	public List<Product> searchProducts(String keyword) {
	    return productRepository.findByNameContainingIgnoreCase(keyword);
	}
	
	
	
	
	
	
	
	
	
	

    
}
