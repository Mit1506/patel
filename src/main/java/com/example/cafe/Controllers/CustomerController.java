package com.example.cafe.Controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cafe.dtos.CartitemDto;
import com.example.cafe.dtos.CategoryDto;
import com.example.cafe.dtos.ProductDto;
import com.example.cafe.dtos.ReservationDto;
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
	  
	  
	  @GetMapping("/{categoryId}/products")
	    public ResponseEntity<List<ProductDto>> getProductByCategory(@PathVariable Long categoryId) {
	    	List<ProductDto> productDtoList = customerService.getProductByCategory(categoryId);
	    	if (productDtoList == null) { 
	    		return ResponseEntity.notFound().build();
	    	}
	    return ResponseEntity.ok(productDtoList);
	}
	  
	  @PostMapping("/reservation")
	    public ResponseEntity<?> postReservation(@RequestBody ReservationDto reservationDto) throws IOException {
	       ReservationDto postedreservationDto = customerService.postReservation(reservationDto);
	       if(postedreservationDto == null) return new ResponseEntity<>("something went wrong", HttpStatus.BAD_REQUEST);
	       return ResponseEntity.status(HttpStatus.CREATED).body(postedreservationDto);
	    }
	  
	  @GetMapping("/reservations/{customerId}")
	    public ResponseEntity<List<ReservationDto>> getReservationByUser(@PathVariable Long customerId) {
	    	List<ReservationDto> reservationDtoList = customerService.getReservationByUser(customerId);
	    	if (reservationDtoList == null) { 
	    		return ResponseEntity.notFound().build();
	    	}
	    return ResponseEntity.ok(reservationDtoList);
	}
	  @PostMapping("/cart")
	  public ResponseEntity<?> postProductToCart(@RequestBody CartitemDto cartitemDto){
		  return customerService.addProductToCart(cartitemDto);
	  }
	  
}