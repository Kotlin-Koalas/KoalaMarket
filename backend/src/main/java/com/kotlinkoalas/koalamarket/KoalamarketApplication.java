package com.kotlinkoalas.koalamarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class KoalamarketApplication {
	// https://spring.io/guides/tutorials/rest
	public static void main(String[] args) {
		SpringApplication.run(KoalamarketApplication.class, args);
	}

}
