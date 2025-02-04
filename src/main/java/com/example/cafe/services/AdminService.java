
package com.example.cafe.services;

import java.io.IOException;
import java.util.List;

import com.example.cafe.dtos.CategoryDto;
import com.example.cafe.dtos.ProductDto;
import com.example.cafe.dtos.ReservationDto;

public interface AdminService {
	
	
	CategoryDto postCategory(CategoryDto categoryDto) throws IOException;

	List<CategoryDto> getAllCategories();

	List<CategoryDto> getAllCategoriesTitle(String title);

	ProductDto postProduct(ProductDto productdto) throws IOException;

	List<ProductDto> getAllProductsByCategory();

	List<ProductDto> getAllProductsByCategoryId(Long categoryId);

	List<ProductDto> getAllProductsByTitle(String title);

	void deleteproduct(Long productId);

	ProductDto updateProduct(Long productId, ProductDto productdto) throws IOException;

	List<ProductDto> getAllProductsById(Long productId);

	List<ReservationDto> getReservation();

	ReservationDto changeReservationStatus(Long reservationId, String status);

}
