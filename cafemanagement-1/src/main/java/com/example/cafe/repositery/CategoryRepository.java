package com.example.cafe.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cafe.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	
}
