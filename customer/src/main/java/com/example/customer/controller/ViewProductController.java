package com.example.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.customer.model.Product;
import com.example.customer.model.User;
import com.example.customer.repository.OrderRepository;
import com.example.customer.service.CartService;
import com.example.customer.service.OrderService;
import com.example.customer.service.ProductService;
import com.example.customer.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
    private OrderService orderService;
	
	@PostMapping("/viewProduct")
	public String viewProduct(@RequestParam int productId,Model model,HttpSession session)
	{
			Product product = productService.viewProduct(productId);
			model.addAttribute("product", product);
			return "viewProduct";
		
	}
	
	@GetMapping("/viewProduct/{id}")
	public String viewProduct(@PathVariable Integer id, Model model) {

	    Product product = productService.viewProduct(id);

	    model.addAttribute("product", product);

	    return "viewProduct"; 
	}

	// Add to Cart
	// Add to Cart
	@PostMapping("/addToCart")
	public String addToCart(@RequestParam Integer productId,
	                        @RequestParam(defaultValue = "1") int quantity,
	                        RedirectAttributes redirectAttributes) {

	    // Get logged-in user
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
	        redirectAttributes.addFlashAttribute("error", "Please login first");
	        return "redirect:/userLogin";
	    }

	    org.springframework.security.core.userdetails.UserDetails userDetails =
	            (org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal();
	    String username = userDetails.getUsername();

	    try {
	        // Pass username to the service
	        productService.addToCart(productId, quantity, username);
	        redirectAttributes.addFlashAttribute("message", "Added to cart successfully!");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	    }

	    return "redirect:/userDashboard";
	}

	@PostMapping("/orderProduct")
	public String orderProduct(@RequestParam Integer productId,
	                           @RequestParam(defaultValue = "1") int quantity,
	                           RedirectAttributes redirectAttributes,
	                           Authentication authentication) {

	    // ✅ Check login
	    if (authentication == null || !authentication.isAuthenticated()
	            || authentication.getPrincipal().equals("anonymousUser")) {

	        redirectAttributes.addFlashAttribute("error", "Please login to place order");
	        return "redirect:/userLogin";
	    }

	    try {
	        // ✅ Call OrderService (correct layer)
	        orderService.placeOrder(productId, quantity);

	        redirectAttributes.addFlashAttribute("message", "Order placed successfully!");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	    }

	    return "redirect:/order";
	}
}


