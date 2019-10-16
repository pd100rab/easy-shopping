package com.ecart.easyshopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OutOfStockException extends RuntimeException{
	
	private String name;

	public OutOfStockException(String name) {
		super(String.format("Sorry, %s is currently out of stock", name));
		this.name = name;
	}
	
	public String getName() {
        return name;
    }

}
