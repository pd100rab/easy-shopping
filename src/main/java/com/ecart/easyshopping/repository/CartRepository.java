package com.ecart.easyshopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecart.easyshopping.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	
	@Query("Select c from Cart c where c.name = :name")
    Optional<Cart> findByItemName(@Param("name")String name);
}
