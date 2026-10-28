package com.example.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.customer.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Category findByName(String categoryname);
	
	Category save(Category category);

}
