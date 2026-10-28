package com.example.commerce.controller;

import java.security.Principal;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.commerce.model.Category;
import com.example.commerce.model.Product;
import com.example.commerce.service.CategoryService;
import com.example.commerce.service.ProductService;


@Controller
public class AddProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addProduct")
    public String addProduct(
            @RequestParam String productname,
            @RequestParam String categoryname,
            @RequestParam("image") MultipartFile image,
            @RequestParam Integer quantity,
            @RequestParam Double price,
            Model model,
            Principal principal) {
    	
		    	// Get logged-in admin username
		        String username = principal.getName();
		        model.addAttribute("adminName", username);


      
            Product product = new Product();
            product.setName(productname);
            product.setQuantity(quantity);
            product.setPrice(price);

            // Category handling
            Category category = categoryService.findCategory(categoryname);

            if (category == null) {
                category = new Category();
                category.setName(categoryname);
                categoryService.add(category);
            }

            product.setCategory(category);

            try {
                String uploadDir = "D:/uploads/";

                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                if (!image.isEmpty()) {
                    String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

                    File file = new File(uploadDir + fileName);
                    image.transferTo(file);

                    product.setImage(fileName);
                } else {
                    product.setImage("default.png");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            productService.add(product);

            model.addAttribute("message", "Product Added Successfully");
            return "redirect:/adminDashboard";
        
    }
}