package com.example.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.customer.model.Product;
import com.example.customer.service.ProductService;

@Controller
public class IndexController {
	
	@Autowired
	private ProductService productService;
	
	
	// Customer Dashboard (no login required)
    @GetMapping("/index")
    public String index() {
        return "index"; 
    }
	
	 // Customer Dashboard (no login required)
    @GetMapping("/userDashboard")
    public String customerDashboard(Model model) {
    	List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "userDashboard"; 
    }

  

}
