package com.kotlinkoalas.koalamarket.controller.products;

import com.kotlinkoalas.koalamarket.model.products.Product;
import com.kotlinkoalas.koalamarket.service.products.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    private final ProductService repository;

    ProductController(ProductService repository) {
        this.repository = repository;
    }

    @GetMapping("/products/{productNumber}/exist")
    public ResponseEntity<?> existProductNumber(@PathVariable String productNumber) {
        return repository.existsProduct(productNumber);
    }

    @GetMapping("/products/{productNumber}")
    public ResponseEntity<Map<String,Object>> getProduct(@PathVariable String productNumber) {
        return repository.getProduct(productNumber);
    }

    @GetMapping("/products")
    public List<Product> allDistinct() {
        return repository.allDistinctProducts();
    }

    @GetMapping("{vendor}/products")
    public List<Product> allVendorProducts(@PathVariable String vendor) {
        return repository.allVendorProducts(vendor);
    }
}
