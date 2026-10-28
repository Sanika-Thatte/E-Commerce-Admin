package com.example.customer.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.customer.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	List<Product> findByProductIdOrNameOrCategory_Name(Integer productid, String productname, String productcategory);

	Product findByProductId(int productId);

	void deleteByProductId(int productId);
	
	List<Product> findTop6ByOrderByProductIdDesc();

	List<Product> findByNameContainingIgnoreCase(String keyword);
	
	

}
