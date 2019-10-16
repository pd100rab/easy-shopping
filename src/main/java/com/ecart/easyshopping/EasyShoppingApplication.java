package com.ecart.easyshopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EasyShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyShoppingApplication.class, args);
	}

}
