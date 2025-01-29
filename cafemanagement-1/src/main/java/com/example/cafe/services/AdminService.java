package com.example.cafe.services;

import java.io.IOException;
import java.util.List;

import com.example.cafe.dtos.CategoryDto;
import com.example.cafe.dtos.ProductDto;


public interface AdminService {
	
	
	 CategoryDto postCategory(CategoryDto categoryDto) throws IOException;

	List<CategoryDto> getAllCategories();

	List<CategoryDto> getAllCategoriesTitle(String title);

	

	ProductDto postProduct(ProductDto productdto) throws IOException;

	List<ProductDto> getAllProductsByCategory();

	List<ProductDto> getAllProductsByCategoryId(Long categoryId);

	void deleteproduct(Long productId);

	List<ProductDto> getAllProductsByTitle(String title);

	ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;

}
