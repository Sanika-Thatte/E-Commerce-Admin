package com.example.commerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.commerce.model.Product;
import com.example.commerce.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
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

}
