package com.example.commerce.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.commerce.model.Admin;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>
{
	    //Signup Admin
	    Optional<Admin> findByUsername(String username);

		

		
}
