package com.example.cafe.servicesimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cafe.dtos.CartitemDto;
import com.example.cafe.dtos.CategoryDto;
import com.example.cafe.dtos.ProductDto;
import com.example.cafe.dtos.ReservationDto;
import com.example.cafe.entities.Cartitems;
import com.example.cafe.entities.Category;
import com.example.cafe.entities.Order;
import com.example.cafe.entities.Product;
import com.example.cafe.entities.Reservation;
import com.example.cafe.entities.User;
import com.example.cafe.enums.OrderStatus;
import com.example.cafe.enums.ReservationStatus;
import com.example.cafe.repositery.CartitemsRepository;
import com.example.cafe.repositery.CategoryRepository;
import com.example.cafe.repositery.OrderRepository;
import com.example.cafe.repositery.ProductRepository;
import com.example.cafe.repositery.ReservationRepository;
import com.example.cafe.repositery.UserRepository;
import com.example.cafe.services.CustomerService;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceimpl implements CustomerService {

	@Autowired
    CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CartitemsRepository cartitemsRepository;
	
	@Autowired
	OrderRepository orderRepository;
//#category
	@Override
	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

	@Override
	public List<CategoryDto> getCategoriesByName(String title) {
		return categoryRepository.findAllByNameContaining(title).stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}
//#product
	@Override
	public List<ProductDto> getProductByCategory(Long categoryId) {
		
		return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDto).collect(Collectors.toList());
	}
	
//#reservation
	@Override
	public ReservationDto postReservation(ReservationDto reservationDto) {
		Optional<User> optionalUser = userRepository.findById(reservationDto.getCustomerId());
		if (optionalUser.isPresent()) {
			Reservation reservation = new Reservation();
			reservation.setTableType(reservationDto.getTableType());
			reservation.setDateTime(reservationDto.getDateTime());
			reservation.setDescription(reservationDto.getDescription());
			reservation.setUser(optionalUser.get());
			reservation.setReservationstatus(ReservationStatus.PENDING);
			Reservation postedReservation = reservationRepository.save(reservation);
			ReservationDto postedReservationDto = new ReservationDto();
			postedReservationDto.setId(postedReservation.getId());

			return postedReservationDto;

		}

		return null;

	}

	@Override
	public List<ReservationDto> getReservationByUser(Long customerId) {
		return reservationRepository.findAllByUserId(customerId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
	}

	
	
//#ADDTOCART
	@Override
	public ResponseEntity<?> addProductToCart(CartitemDto cartitemDto) {
		Order pendingOrder=orderRepository.findByUserIdAndOrderStatus(cartitemDto.getUserId(),OrderStatus.PENDIN);
		Optional<Cartitems>optional=cartitemsRepository.findByUserIdAndProductIdAndOrderId(
				cartitemDto.getUserId(),
				cartitemDto.getProductid(),
				cartitemDto.getOrderid());
		if(optional.isPresent())
		{
			CartitemDto productAlreadyExsisInCart=new CartitemDto();
			productAlreadyExsisInCart.setProductid(null);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(productAlreadyExsisInCart);
		}
		else
		{
			Optional<Product> optionalproduct=productRepository.findById(cartitemDto.getProductid());
			Optional<User> optionaluser=userRepository.findById(cartitemDto.getUserId());
			if(optionalproduct.isPresent() && optionaluser.isPresent()) 
			{
				Product product=optionalproduct.get();
				Cartitems cartitems=new Cartitems();
				cartitems.setProduct(product);
				cartitems.setUser(optionaluser.get());
				cartitems.setQuantity(1L);
				cartitems.setOrder(pendingOrder);
				cartitems.setPrice(product.getPrice());
				Cartitems updatecart=cartitemsRepository.save(cartitems);
				pendingOrder.setPrice(pendingOrder.getPrice()+cartitems.getPrice());
				pendingOrder.getCartitems().add(cartitems);
				orderRepository.save(pendingOrder);
				CartitemDto updatedCartitemDto=new CartitemDto();
				updatedCartitemDto.setId(cartitems.getId());
				return ResponseEntity.status(HttpStatus.CREATED).body(updatedCartitemDto);
				
			}
			else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
			}
		}
		
	}

	


}