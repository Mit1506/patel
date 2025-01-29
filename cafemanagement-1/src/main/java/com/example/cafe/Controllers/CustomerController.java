package com.example.cafe.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cafe.dtos.CategoryDto;
import com.example.cafe.services.CustomerService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	  @GetMapping("/getcategories")
	    public ResponseEntity<List<CategoryDto>> getAllCategories() {
	    	List<CategoryDto> categoryDtoList = customerService.getAllCategories();
	    	if (categoryDtoList == null) { 
	    		return ResponseEntity.notFound().build();
	    	}
	    return ResponseEntity.ok(categoryDtoList);
	}
	  
	  @GetMapping("/getcategories/{title}")
	    public ResponseEntity<List<CategoryDto>> getCategoriesByName(@PathVariable String title) {
	    	List<CategoryDto> categoryDtoList = customerService.getCategoriesByName(title);
	    	if (categoryDtoList == null) { 
	    		return ResponseEntity.notFound().build();
	    	}
	    return ResponseEntity.ok(categoryDtoList);
	}
}