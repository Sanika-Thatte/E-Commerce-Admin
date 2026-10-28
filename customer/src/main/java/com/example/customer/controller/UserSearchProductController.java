package com.example.customer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.customer.model.Product;
import com.example.customer.service.ProductService;


@Controller
public class UserSearchProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/searchUserProduct")
	public String searchProduct(
	        @RequestParam(required = false) Integer productid,
	        @RequestParam(required = false) String productname,
	        @RequestParam(required = false) String productcategory,
	        @RequestParam(required = false) String seeData,
	        Model model) {

	    // ✅ Initialize first
	    List<Product> products = new ArrayList<>();

	    if ("all".equals(seeData)) {
	        products = productService.findAllProducts();
	    } 
	    else if (productid != null 
	          || (productname != null && !productname.isEmpty()) 
	          || (productcategory != null && !productcategory.isEmpty())) {

	        products = productService
	                .findByProductIdOrProductNameOrProductCategory(
	                        productid, productname, productcategory);

	        if (products == null || products.isEmpty()) {
	            model.addAttribute("error", "Product not found");
	        }
	    }

	    model.addAttribute("products", products);

	    return "searchProduct";
	}
	
	
	@GetMapping("/searchBar")
	public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
	    List<Product> products = productService.searchProducts(keyword);
	    model.addAttribute("products", products);
	    return "userDashboard";  // show results on same page
	}

}
