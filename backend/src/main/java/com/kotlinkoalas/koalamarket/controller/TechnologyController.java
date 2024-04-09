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


    @PutMapping("/products/technology/{productNumber}")
    Technology replaceTechnology(@RequestBody Technology newTechnology, @PathVariable String productNumber) {
        Technology oldTechnology = repository.findByProductNumber(productNumber);
        oldTechnology.setPrice(newTechnology.getPrice());
        oldTechnology.setDescription(newTechnology.getDescription());
        oldTechnology.setBrand(newTechnology.getBrand());
        oldTechnology.setEcology(newTechnology.getEcology());
        oldTechnology.setElectricConsumption(newTechnology.getElectricConsumption());
        oldTechnology.setName(newTechnology.getName());
        oldTechnology.setStock(newTechnology.getStock());
        oldTechnology.setImage(newTechnology.getImage());
        return repository.save(oldTechnology);
    }

    @DeleteMapping("/products/technology/{productNumber}")
    String deleteTechnology(@PathVariable String productNumber) {
        repository.deleteByProductNumber(productNumber);
        return "Successfully deleted";
    }
}
