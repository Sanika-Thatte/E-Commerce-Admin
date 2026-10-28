package com.example.commerce.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.commerce.model.Product;
import com.example.commerce.service.ProductService;

@Controller
public class EditDeleteController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/editProduct")
	public String editProduct(@RequestParam int productId, Model model, Principal principal) {
		// Get logged-in admin username
	    String username = principal.getName();
	    model.addAttribute("adminName", username);

	    Product product = productService.viewProduct(productId);
	    model.addAttribute("product", product);
	    return "updateProduct"; // new page
	}
	
	@PostMapping("/deleteProduct")
	public String deleteProduct(@RequestParam int productId, RedirectAttributes redirectAttributes ,Model model,Principal principal) {
		
		// Get logged-in admin username
	    String username = principal.getName();
	    model.addAttribute("adminName", username);

	    
	    productService.delete(productId);
	    
	    redirectAttributes.addFlashAttribute("message", "Product Deleted Successfully");
	    
	    return "redirect:/adminDashboard";   
	}
	
	@GetMapping("/adminDashboard")
	public String adminDashboard(Model model ,Principal principal) {
		
		// Get logged-in admin username
	    String username = principal.getName();
	    model.addAttribute("adminName", username);


	    model.addAttribute("products", productService.findAllProducts()); // load data

	    return "adminDashboard";
	}

}
