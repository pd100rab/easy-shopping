package com.ecart.easyshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecart.easyshopping.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>{

	@Query("Select s from Stock s where s.name = :name")
    Stock findByStockName(@Param("name")String name);
}
