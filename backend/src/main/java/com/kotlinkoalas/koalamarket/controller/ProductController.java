package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.repo.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductRepository repository;

    ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    public ResponseEntity<?> existProductNumber(@RequestBody String productNumber) {
        return ResponseEntity.ok(repository.existsByProductNumber(productNumber));
    }


}
