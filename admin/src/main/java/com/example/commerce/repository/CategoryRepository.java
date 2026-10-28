package com.example.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.commerce.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Category findByName(String categoryname);
	
	Category save(Category category);

}
