package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.factory.FoodFactory;
import com.kotlinkoalas.koalamarket.factory.ProductFactory;
import com.kotlinkoalas.koalamarket.model.Food;
import com.kotlinkoalas.koalamarket.repo.FoodRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FoodController {

    private final FoodRepository repository;

    FoodController(FoodRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products/foods")
    List<Food> all() {
        return repository.findAll();
    }

    @PostMapping("/products/foods")
    public Food newFood(@RequestBody Map<String, Object> payload) {
        String productNumber = (String) payload.get("productNumber");
        String name = (String) payload.get("name");
        String stringPrice = (String) payload.get("price");
        double price = Double.parseDouble(stringPrice);
        String description = (String) payload.get("description");
        String ecology = (String) payload.get("ecology");
        int stock = (int) payload.get("stock");
        String image = (String) payload.get("image");
        String cif = (String) payload.get("cif");
        
        String calories = (String) payload.get("calories");
        String macros = (String) payload.get("macros");

        if (repository.existsByProductNumberAndCif(productNumber, cif)) {
            throw new RuntimeException("Product number and cif already exists");
        }

        ProductFactory FoodFactory = new FoodFactory();
        Food food = (Food) FoodFactory.createProduct(productNumber, name, price, description, ecology, stock, image, cif, calories, macros);

        return repository.save(food);
    }


    @PutMapping("/products/foods/{productNumber}")
    Food replaceFood(@RequestBody Map<String, Object> payload, @PathVariable String productNumber) {
        Food oldFood = repository.findByProductNumber(productNumber);
        String stringPrice = (String) payload.get("price");
        oldFood.setPrice(Double.parseDouble(stringPrice));
        oldFood.setDescription((String) payload.get("description"));
        oldFood.setMacros((String) payload.get("macros"));
        oldFood.setEcology((String) payload.get("ecology"));
        oldFood.setCalories((Integer) payload.get("calories"));
        oldFood.setName((String) payload.get("name"));
        oldFood.setStock((int) payload.get("stock"));
        oldFood.setImage((String) payload.get("image"));
        return repository.save(oldFood);
    }

    @DeleteMapping("/products/foods/{productNumber}")
    String deleteFood(@PathVariable String productNumber) {
        repository.deleteByProductNumber(productNumber);
        return "Successfully deleted";
    }
}
