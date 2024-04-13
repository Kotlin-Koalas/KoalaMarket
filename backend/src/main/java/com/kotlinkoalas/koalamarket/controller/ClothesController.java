package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.factory.ClothesFactory;
import com.kotlinkoalas.koalamarket.factory.ProductFactory;
import com.kotlinkoalas.koalamarket.model.Clothes;

import com.kotlinkoalas.koalamarket.repo.ClothesRepository;
import com.kotlinkoalas.koalamarket.repo.TechnologyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ClothesController {

    private final ClothesRepository repository;

    ClothesController(ClothesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products/clothes")
    List<Clothes> all() {
        return repository.findAll();
    }

    @PostMapping("/products/clothes")
    public Clothes newClothes(@RequestBody Map<String, Object> payload) {
        String productNumber = (String) payload.get("productNumber");
        String name = (String) payload.get("name");
        double price = (double) payload.get("price");
        String description = (String) payload.get("description");
        String ecology = (String) payload.get("ecology");
        int stock = (int) payload.get("stock");
        String image = (String) payload.get("image");
        String cif = (String) payload.get("cif");
        String color = (String) payload.get("color");
        String size = (String) payload.get("size");

        if (repository.existsByProductNumberAndCif(productNumber, cif)) {
            throw new RuntimeException("Product number and cif already exists");
        }

        ProductFactory ClothesFactory = new ClothesFactory();
        Clothes clothes = (Clothes) ClothesFactory.createProduct(productNumber, name, price, description, ecology, stock, image, cif, color, size);

        return repository.save(clothes);
    }


    @PutMapping("/products/clothes/{productNumber}")
    Clothes replaceClothes(@RequestBody Map<String, Object> payload, @PathVariable String productNumber) {
        Clothes oldClothes = repository.findByProductNumber(productNumber);
        oldClothes.setPrice((double) payload.get("price"));
        oldClothes.setDescription((String) payload.get("description"));
        oldClothes.setColor((String) payload.get("color"));
        oldClothes.setEcology((String) payload.get("ecology"));
        oldClothes.setSize((String) payload.get("size"));
        oldClothes.setName((String) payload.get("name"));
        oldClothes.setStock((int) payload.get("stock"));
        oldClothes.setImage((String) payload.get("image"));
        return repository.save(oldClothes);
    }

    @DeleteMapping("/products/clothes/{productNumber}")
    String deleteClothes(@PathVariable String productNumber) {
        repository.deleteByProductNumber(productNumber);
        return "Successfully deleted";
    }
}
