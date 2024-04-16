package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Product;
import com.kotlinkoalas.koalamarket.repo.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private final ProductRepository repository;

    ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products/{productNumber}/exist")
    public ResponseEntity<?> existProductNumber(@PathVariable String productNumber) {
        return ResponseEntity.ok(repository.existsByProductNumber(productNumber));
    }

    @GetMapping("/products/{productNumber}")
    public List<Product> getProduct(@PathVariable String productNumber) {
        return repository.findByProductNumber(productNumber);
    }

    @GetMapping("/products")
    public List<Product> allDistinct() {
        List<String> productNumbers = repository.findDistinctProducts();
        List<Product> products = new ArrayList<>();
        for (String productNumber : productNumbers) {
            products.add(repository.findByProductNumber(productNumber).get(0));
        }
        return products;
    }

}
