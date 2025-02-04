package com.example.cafe.repositery;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cafe.entities.Cartitems;

@Repository
public interface CartitemsRepository extends JpaRepository<Cartitems, Long>{

	Optional<Cartitems> findByUserIdAndProductIdAndOrderId(Long userId, Long productid, Long orderid);

}
