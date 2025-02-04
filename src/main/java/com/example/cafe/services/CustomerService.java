package com.example.cafe.services;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;


import com.example.cafe.dtos.CartitemDto;
import com.example.cafe.dtos.CategoryDto;
import com.example.cafe.dtos.ProductDto;
import com.example.cafe.dtos.ReservationDto;

public interface CustomerService{

	List<CategoryDto> getAllCategories();

	List<CategoryDto> getCategoriesByName(String title);

	List<ProductDto> getProductByCategory(Long categoryId);

	ReservationDto postReservation(ReservationDto reservationDto) throws IOException;

	List<ReservationDto> getReservationByUser(Long customerId);

	ResponseEntity<?> addProductToCart(CartitemDto cartitemDto);
}
