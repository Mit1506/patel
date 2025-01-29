package com.example.cafe.Controllers;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cafe.dtos.CategoryDto;
import com.example.cafe.dtos.ProductDto;

import com.example.cafe.services.AdminService;



@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AdminController {
	
	
	@Autowired
     AdminService adminService;
	
	 
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> postCategory(@ModelAttribute CategoryDto categoryDto) throws IOException {
       CategoryDto createdCategoryDto = adminService.postCategory(categoryDto);
       if(createdCategoryDto == null) return ResponseEntity.notFound().build();
       return ResponseEntity.ok(createdCategoryDto);
    }
    
    @GetMapping("/getcategories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
    	List<CategoryDto> categoryDtoList = adminService.getAllCategories();
    	if (categoryDtoList == null) return ResponseEntity.notFound().build();
    	return ResponseEntity.ok(categoryDtoList);
    }
    @GetMapping("/getcategories/{title}")
    public ResponseEntity<List<CategoryDto>> getAllCategoriesTitle(@PathVariable String title) {
    	List<CategoryDto> categoryDtoList = adminService.getAllCategoriesTitle(title);
    	if (categoryDtoList == null) return ResponseEntity.notFound().build();
    	return ResponseEntity.ok(categoryDtoList);
    }
    
    @PostMapping("/product")
    public ResponseEntity<?> postProduct(@ModelAttribute ProductDto productdto) throws IOException {
    	ProductDto createdProductDto = adminService.postProduct(productdto);
       if(createdProductDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
       return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
    }
    
    @GetMapping("/getproducts")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategory() {
    	List<ProductDto> productDtoList = adminService.getAllProductsByCategory();
    	if (productDtoList == null) { 
    		return ResponseEntity.notFound().build();
    	}
    return ResponseEntity.ok(productDtoList);
  
}
    @GetMapping("/{categoryId}/getproducts")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryId(@PathVariable Long categoryId) {
        // Pass categoryId to the service method
        List<ProductDto> productDtoList = adminService.getAllProductsByCategoryId(categoryId);

        // Check if the product list is null or empty and return a proper response
        if (productDtoList == null || productDtoList.isEmpty()) {
            return ResponseEntity.notFound().build();  // Return 404 if no products are found
        }

        return ResponseEntity.ok(productDtoList);  // Return 200 with the list of products
        }
    
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteproduct(@PathVariable Long productId) {
     adminService.deleteproduct(productId);
    	
    return ResponseEntity.noContent().build();
    }
    
    
    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) throws IOException {
    	ProductDto updateProductDto=adminService.updateProduct(productId, productDto);
    	if (updateProductDto == null)
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
    		return ResponseEntity.status(HttpStatus.OK).body (updateProductDto);
}
}