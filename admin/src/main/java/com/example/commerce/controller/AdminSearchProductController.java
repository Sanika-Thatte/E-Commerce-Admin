package com.example.commerce.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import com.example.commerce.model.Product;
import com.example.commerce.service.ProductService;


@Controller
public class AdminSearchProductController {
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/searchAdminProduct")
	public String searchProduct(@RequestParam(required = false) Integer productid, @RequestParam(required = false) String productname, @RequestParam(required = false) String productcategory,@RequestParam(required = false) String seeData,Model model,Principal principal)
	{
		
		// Get logged-in admin username
	    String username = principal.getName();
	    model.addAttribute("adminName", username);
	    
	        List<Product> products = new ArrayList<>();
			if("all".equals(seeData))
			{
				products=productService.findAllProducts();
			}
			
			else if (productid != null 
			          || (productname != null && !productname.isEmpty()) 
			          || (productcategory != null && !productcategory.isEmpty())) {
				
				products=productService.findByProductIdOrProductNameOrProductCategory(productid,productname,productcategory);
				if(products==null || products.isEmpty())
				{
					model.addAttribute("error", "Product not found");
				}
				
			}
			model.addAttribute("products", products);

	        return "adminDashboard";
	        
	
	}
	
	
	

}
