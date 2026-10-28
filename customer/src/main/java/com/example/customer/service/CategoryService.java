package com.example.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.model.Category;
import com.example.customer.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	public Category findCategory(String categoryname) {
		
		return categoryRepository.findByName(categoryname);
	}

	public Category add(Category category) {
		return categoryRepository.save(category);
		
	}

}
