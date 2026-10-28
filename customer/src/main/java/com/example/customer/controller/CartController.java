package com.example.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.customer.model.CartItem;
import com.example.customer.model.User;
import com.example.customer.service.CartService;
import com.example.customer.service.UserService;

@Controller
public class CartController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/cart")
	public String viewCart(Model model, RedirectAttributes redirectAttributes) {

	    // Get the authentication object
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    // If not logged in, redirect to login page with error
	    if (principal == null || principal.equals("anonymousUser")) {
	        redirectAttributes.addFlashAttribute("error", "Please login to view your cart");
	        return "redirect:/userLogin";
	    }

	    // Cast principal to UserDetails safely
	    org.springframework.security.core.userdetails.UserDetails userDetails =
	            (org.springframework.security.core.userdetails.UserDetails) principal;

	 // Get logged-in username
	    String username = userDetails.getUsername();

	    // Fetch User object
	    User user = userService.findUsername(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // Fetch cart items by userId
	    List<CartItem> cartItems = cartService.getCartItems(username);
	    
	    double totalAmount = cartItems.stream()
	            .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
	            .sum();
	    
	    model.addAttribute("cartItems", cartItems);

	    model.addAttribute("totalAmount", totalAmount);

	   

	    return "cart";
	}
	
	// =========================================
    // 2️⃣ Order entire cart
    // =========================================
    @PostMapping("/orderCart")
    public String orderCart(RedirectAttributes redirectAttributes) {

        // ✅ Check user login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            redirectAttributes.addFlashAttribute("error", "Please login to place order");
            return "redirect:/userLogin";
        }

        String username = auth.getName();
        User user = userService.findUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            cartService.orderCart(user.getUserId());
            redirectAttributes.addFlashAttribute("message", "Cart ordered successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/order"; 
    }

    // =========================================
    // 3️⃣ Order single cart item
    // =========================================
    @PostMapping("/orderCartItem")
    public String orderCartItem(@RequestParam Integer cartItemId,
                                RedirectAttributes redirectAttributes) {

        // ✅ Check user login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            redirectAttributes.addFlashAttribute("error", "Please login to place order");
            return "redirect:/userLogin";
        }

        try {
            cartService.orderSingleCartItem(cartItemId);
            redirectAttributes.addFlashAttribute("message", "Item ordered successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/order"; 
    }


}
