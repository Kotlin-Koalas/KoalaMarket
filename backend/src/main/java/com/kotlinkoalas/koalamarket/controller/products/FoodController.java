package com.kotlinkoalas.koalamarket.controller.products;

import com.kotlinkoalas.koalamarket.model.products.Food;
import com.kotlinkoalas.koalamarket.service.products.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FoodController {

    private final FoodService foodService;

    FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/products/foods")
    List<Food> all() {
        return foodService.getAllFoods();
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

        return foodService.createFood(productNumber,name,price,description,ecology,stock,image,cif,calories,macros);
    }


    @PutMapping("/products/foods/{productNumber}")
    Food replaceFood(@RequestBody Map<String, Object> payload, @PathVariable String productNumber) {
        String stringPrice = (String) payload.get("price");
        double price = Double.parseDouble(stringPrice);
        String description = (String) payload.get("description");
        String ecology = (String) payload.get("ecology");
        String name = (String) payload.get("name");
        int stock = (int) payload.get("stock");
        String image = (String) payload.get("image");

        int calories = (int) payload.get("calories");
        String macros = (String) payload.get("macros");

        return foodService.updateFood(productNumber,name,price,description,ecology,stock,image,macros,calories);
    }

    @DeleteMapping("/products/foods/{productNumber}")
    String deleteFood(@PathVariable String productNumber) {
        foodService.deleteFood(productNumber);
        return "Successfully deleted";
    }
}
