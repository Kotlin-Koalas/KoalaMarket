package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Food;
import com.kotlinkoalas.koalamarket.repo.FoodRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @GetMapping("/persons/{id}")
//    Person one(@PathVariable Long id) {
//    }

    @PostMapping("/products/foods")
    public Food newFood(@RequestBody Food food) {
        return repository.save(food);
    }


    @PutMapping("/products/foods/{productNumber}")
    Food replaceFood(@RequestBody Food newFood, @PathVariable String productNumber) {
        Food oldFood = repository.findByProductNumber(productNumber);
        oldFood.setPrice(newFood.getPrice());
        oldFood.setDescription(newFood.getDescription());
        oldFood.setMacros(newFood.getMacros());
        oldFood.setEcology(newFood.getEcology());
        oldFood.setCalories(newFood.getCalories());
        oldFood.setName(newFood.getName());
        oldFood.setStock(newFood.getStock());
        oldFood.setImage(newFood.getImage());
        return repository.save(oldFood);
    }

    @DeleteMapping("/products/foods/{productNumber}")
    String deleteFood(@PathVariable String productNumber) {
        repository.deleteByProductNumber(productNumber);
        return "Successfully deleted";
    }
}
