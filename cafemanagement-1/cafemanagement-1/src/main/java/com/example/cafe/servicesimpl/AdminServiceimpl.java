package com.example.cafe.servicesimpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cafe.dtos.CategoryDto;
import com.example.cafe.entities.Category;
import com.example.cafe.repositery.CategoryRepository;
import com.example.cafe.services.AdminService;



@Service

public class AdminServiceimpl implements AdminService{
	
	@Autowired
     CategoryRepository categoryRepository;

    @Override

    public CategoryDto postCategory(CategoryDto categoryDto) throws IOException {
        
    	Category category = new Category();
        
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setImg(categoryDto.getImg().getBytes());
        Category createdCategory = categoryRepository.save(category);
        CategoryDto createdCategoryDto = new CategoryDto();
        createdCategoryDto.setId(createdCategory.getId());
        
        
        return createdCategoryDto;
    }
}
