package com.example.cafe.servicesimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cafe.dtos.CategoryDto;
import com.example.cafe.dtos.ProductDto;
import com.example.cafe.dtos.ReservationDto;
import com.example.cafe.entities.Category;
import com.example.cafe.entities.Product;
import com.example.cafe.entities.Reservation;
import com.example.cafe.enums.ReservationStatus;
import com.example.cafe.repositery.CategoryRepository;
import com.example.cafe.repositery.ProductRepository;
import com.example.cafe.repositery.ReservationRepository;
import com.example.cafe.services.AdminService;



@Service

public class AdminServiceimpl implements AdminService{
	
	@Autowired
     CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
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
    
    @Override
	public List<CategoryDto> getAllCategories() {
		
		return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

    @Override
	public List<CategoryDto> getAllCategoriesTitle(String title) {
		
		return categoryRepository.findAllByNameContaining(title).stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

	@Override
	 public ProductDto postProduct(ProductDto productDto) throws IOException {
        // Fetch the category using categoryId from the ProductDto
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow();

        // Create a new Product entity and set the Category
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);  // Set the category
        product.setImg(productDto.getImg().getBytes());
        // Save the product to the database
        product = productRepository.save(product);

        // Convert the Product entity back to ProductDto to return
        ProductDto createdProductDto = new ProductDto();
        createdProductDto.setCategoryId(product.getCategory().getId());
        createdProductDto.setName(product.getName());
        createdProductDto.setDescription(product.getDescription());
        createdProductDto.setPrice(product.getPrice());
        // Set other fields as needed

        return createdProductDto;
    }

	@Override
	public List<ProductDto> getAllProductsByCategory() {
	
		return productRepository.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> getAllProductsByCategoryId(Long categoryId) {
		// TODO Auto-generated method stub
		 return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> getAllProductsByTitle(String title) {
		 return productRepository.findAllByNameContaining(title).stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public void deleteproduct(Long productId) {
	    Optional<Product> optionalProduct = productRepository.findById(productId); // Corrected variable name capitalization
	    if (optionalProduct.isPresent()) {
	        productRepository.deleteById(productId);
	    } else {
	        throw new IllegalArgumentException("Product with id: " + productId + " not found");
	    }
	}

	@Override
	public ProductDto updateProduct(Long productId, ProductDto productdto) throws IOException {
		 Optional<Product> optionalProduct = productRepository.findById(productId); // Corrected variable name capitalization
		    if (optionalProduct.isPresent()) {
		    	Product product = optionalProduct.get();
		    	  product.setName(productdto.getName());
		          product.setPrice(productdto.getPrice());
		          product.setDescription(productdto.getDescription());
		          if(productdto.getImg() != null) {
		        	  product.setImg(productdto.getImg().getBytes());
		          }
		          Product  updatedproduct= productRepository.save(product);
		          ProductDto updatedproductdto = new ProductDto();
		          updatedproduct.setId(updatedproduct.getId());
		          return updatedproductdto;
		    }
		    return null;
	}

	@Override
	public List<ProductDto> getAllProductsById(Long productId) {
		 return productRepository.findAllById(productId).stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public List<ReservationDto> getReservation() {
		return reservationRepository.findAll().stream().map(Reservation::getReservationDto).collect(Collectors.toList());
	}
	


	@Override
	public ReservationDto changeReservationStatus(Long reservationId, String status) {
		Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
		if (optionalReservation.isPresent()) {
			Reservation existingReservation = optionalReservation.get();
			// Use the enum to directly handle the status
			try {
				// Assuming 'status' is coming as "APPROVED" or "DISAPPROVED"
				ReservationStatus reservationStatus = ReservationStatus.valueOf(status.toUpperCase());
				// Set the reservation status based on the passed value
				existingReservation.setReservationstatus(reservationStatus);
				// Save updated reservation
				Reservation updatedReservation = reservationRepository.save(existingReservation);
				// Map to ReservationDto and return it
				ReservationDto updatedReservationDto = new ReservationDto();
				updatedReservationDto.setId(updatedReservation.getId());
				updatedReservationDto.setReservationstatus(updatedReservation.getReservationstatus());
				return updatedReservationDto;
			} catch (IllegalArgumentException e) {
				// Handle invalid status input
				return null; // Return null if invalid status is provided
			}
		}

		return null; // Return null if reservation not found
	}
		
}