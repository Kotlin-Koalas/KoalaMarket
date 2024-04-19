package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.factory.ClothesFactory;
import com.kotlinkoalas.koalamarket.factory.ProductFactory;
import com.kotlinkoalas.koalamarket.model.Clothes;

import com.kotlinkoalas.koalamarket.repo.ClothesRepository;
import com.kotlinkoalas.koalamarket.repo.TechnologyRepository;
import com.kotlinkoalas.koalamarket.service.ClothesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ClothesController {

    private final ClothesService clothesService;

    public ClothesController(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @GetMapping("/products/clothes")
    public List<Clothes> getAllClothes() {
        return clothesService.getAllClothes();
    }

    @PostMapping("/products/clothes")
    public Clothes newClothes(@RequestBody Map<String, Object> payload) {
        String productNumber = (String) payload.get("productNumber");
        String name = (String) payload.get("name");
        String stringPrice = (String) payload.get("price");
        double price = Double.parseDouble(stringPrice);
        String description = (String) payload.get("description");
        String ecology = (String) payload.get("ecology");
        int stock = (int) payload.get("stock");
        String image = (String) payload.get("image");
        String cif = (String) payload.get("cif");

        String color = (String) payload.get("color");
        String size = (String) payload.get("size");

        return clothesService.createClothes(productNumber, name, price, description, ecology, stock, image, cif, color, size);
    }


    @PutMapping("/products/clothes/{productNumber}")
    public Clothes replaceClothes(@RequestBody Map<String, Object> payload, @PathVariable String productNumber) {
        String stringPrice = (String) payload.get("price");
        double price = Double.parseDouble(stringPrice);
        String description = (String) payload.get("description");
        String ecology = (String) payload.get("ecology");
        String color = (String) payload.get("color");
        String size = (String) payload.get("size");
        String name = (String) payload.get("name");
        int stock = (int) payload.get("stock");
        String image = (String) payload.get("image");

        return clothesService.updateClothes(productNumber, name, price, description, ecology, stock, image, color, size);
    }

    @DeleteMapping("/products/clothes/{productNumber}")
    public String deleteClothes(@PathVariable String productNumber) {
        clothesService.deleteClothes(productNumber);
        return "Successfully deleted";
    }
}
