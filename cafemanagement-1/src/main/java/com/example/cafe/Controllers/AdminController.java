package com.example.cafe.Controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cafe.dtos.CategoryDto;
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
}