package com.example.cafe.services;

import java.util.List;

import com.example.cafe.dtos.CategoryDto;

public interface CustomerService{

	List<CategoryDto> getAllCategories();

	List<CategoryDto> getCategoriesByName(String title);

}
