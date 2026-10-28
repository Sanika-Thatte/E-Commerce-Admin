package com.example.commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.commerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	List<Product> findByProductIdOrNameOrCategory_Name(Integer productid, String productname, String productcategory);
	
	Product save(Product product);

	Product findByProductId(int productId);

	void deleteByProductId(int productId);

}
