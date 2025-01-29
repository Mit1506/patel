package com.example.cafe.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cafe.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	List<Category> findAllByNameContaining(String title);

	
}
