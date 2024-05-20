package com.kotlinkoalas.koalamarket.controller.products;

import com.kotlinkoalas.koalamarket.service.products.SatisfactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SatisfactionController {

    private final SatisfactionService repository;

    SatisfactionController(SatisfactionService repository) {
        this.repository = repository;
    }

    @PostMapping("/products/{productNumber}/satisfaction")
    public ResponseEntity<String> addSatisfaction(@PathVariable String productNumber, @RequestBody Map<String, Object> payload) {
        String dni = (String) payload.get("dni");
        double satisfaction = (double) payload.get("satisfaction");
        return repository.addSatisfaction(productNumber, dni, satisfaction);
    }

    @PutMapping("/products/{productNumber}/satisfaction")
    public ResponseEntity<String> updateSatisfaction(@PathVariable String productNumber, @RequestBody Map<String, Object> payload) {
        String dni = (String) payload.get("dni");
        double satisfaction = (double) payload.get("satisfaction");
        return repository.updateSatisfaction(productNumber, dni, satisfaction);
    }
}
