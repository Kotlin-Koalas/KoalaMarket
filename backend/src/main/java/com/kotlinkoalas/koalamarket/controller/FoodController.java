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


    @PutMapping("/products/foods/{id}")
    Food replaceFood(@RequestBody Food newFood, @PathVariable String id) {

        return repository.findById(id)
                .map(person -> {
                    person.setName(newFood.getName());
                    return repository.save(person);
                })
                .orElseGet(() -> {
                    newFood.setProductNumber(id);
                    return repository.save(newFood);
                });
    }

    @DeleteMapping("/products/foods/{id}")
    String deleteFood(@PathVariable String id) {
        repository.deleteById(id);
        return "Successfully deleted";
    }
}
