
package com.example.cafe.repositery;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



import com.example.cafe.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findAllByCategoryId(Long categoryId);

	List<Product> findAllByNameContaining(String title);

	List<Product> findAllById(Long productId);

}
