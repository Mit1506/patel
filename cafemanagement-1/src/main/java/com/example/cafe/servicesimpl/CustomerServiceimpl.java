package com.example.cafe.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cafe.dtos.CategoryDto;
import com.example.cafe.entities.Category;
import com.example.cafe.repositery.CategoryRepository;
import com.example.cafe.services.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceimpl implements CustomerService {

	@Autowired
    CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

	@Override
	public List<CategoryDto> getCategoriesByName(String title) {
		return categoryRepository.findAllByNameContaining(title).stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

}