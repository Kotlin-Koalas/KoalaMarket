package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Food;
import com.kotlinkoalas.koalamarket.model.Technology;
import com.kotlinkoalas.koalamarket.repo.FoodRepository;
import com.kotlinkoalas.koalamarket.repo.TechnologyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TechnologyController {

    private final TechnologyRepository repository;

    TechnologyController(TechnologyRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products/technology")
    List<Technology> all() {
        return repository.findAll();
    }

//    @GetMapping("/persons/{id}")
//    Person one(@PathVariable Long id) {
//    }

    @PostMapping("/products/technology")
    public Technology newTechnology(@RequestBody Technology technology) {
        return repository.save(technology);
    }


    @PutMapping("/products/technology/{id}")
    Technology replaceTechnology(@RequestBody Technology newTechnology, @PathVariable String id) {

        return repository.findById(id)
                .map(technology -> {
                    technology.setName(technology.getName());
                    return repository.save(technology);
                })
                .orElseGet(() -> {
                    newTechnology.setProductNumber(id);
                    return repository.save(newTechnology);
                });
    }

    @DeleteMapping("/products/technology/{id}")
    String deleteTechnology(@PathVariable String id) {
        repository.deleteById(id);
        return "Successfully deleted";
    }
}
