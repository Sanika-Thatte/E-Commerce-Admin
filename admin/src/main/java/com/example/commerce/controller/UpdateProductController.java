package com.example.commerce.controller;

import java.io.File;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.commerce.model.Product;
import com.example.commerce.service.ProductService;

@Controller
public class UpdateProductController {
	
	@Autowired
	private ProductService productService;

	@PostMapping("/updateProduct")
	public String updateProduct(
	        @RequestParam int productId,
	        @RequestParam String name,
	        @RequestParam int quantity,
	        @RequestParam double price,
	        @RequestParam("image") MultipartFile image,
	        Model model,
	        RedirectAttributes redirectAttributes ,Principal principal) {
		
		// Get logged-in admin username
	    String username = principal.getName();
	    model.addAttribute("adminName", username);

	    Product p = productService.viewProduct(productId);

	    p.setName(name);
	    p.setQuantity(quantity);
	    p.setPrice(price);

	    try {
	        if (!image.isEmpty()) {

	            String uploadDir = "D:/uploads/";   // ✅ COMMON PATH

	            File dir = new File(uploadDir);
	            if (!dir.exists()) {
	                dir.mkdirs();
	            }

	            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

	            File file = new File(uploadDir + fileName);
	            image.transferTo(file);

	            // ✅ update image only if new one uploaded
	            p.setImage(fileName);
	        }
	        // ❗ if no image selected → old image remains

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    productService.add(p);
	    model.addAttribute("message", "Product Updated Successfully");

	    return "redirect:/adminDashboard";
	}

}
