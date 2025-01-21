package com.example.cafe.services;

import java.io.IOException;

import com.example.cafe.dtos.CategoryDto;

public interface AdminService {
	
	
	 CategoryDto postCategory(CategoryDto categoryDto) throws IOException;

}
