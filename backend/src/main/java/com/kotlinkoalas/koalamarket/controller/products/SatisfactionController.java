package com.kotlinkoalas.koalamarket.controller.products;

import com.kotlinkoalas.koalamarket.service.products.SatisfactionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SatisfactionController {

    private final SatisfactionService repository;

    SatisfactionController(SatisfactionService repository) {
        this.repository = repository;
    }

    @PostMapping("/products/{productNumber}/satisfaction")
    public void addSatisfaction(@PathVariable String productNumber, @RequestBody Map<String, Object> payload) {
        String dni = (String) payload.get("dni");
        double satisfaction = (double) payload.get("satisfaction");
        repository.addSatisfaction(productNumber, dni, satisfaction);
    }

    @PutMapping("/products/{productNumber}/satisfaction")
    public void updateSatisfaction(@PathVariable String productNumber, @RequestBody Map<String, Object> payload) {
        String dni = (String) payload.get("dni");
        double satisfaction = (double) payload.get("satisfaction");
        repository.updateSatisfaction(productNumber, dni, satisfaction);
    }
}
