package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Clothes;
import com.kotlinkoalas.koalamarket.model.Food;
import com.kotlinkoalas.koalamarket.model.Technology;
import com.kotlinkoalas.koalamarket.repo.ClothesRepository;
import com.kotlinkoalas.koalamarket.repo.TechnologyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @GetMapping("/persons/{id}")
//    Person one(@PathVariable Long id) {
//    }

    @PostMapping("/products/clothes")
    public Clothes newClothes(@RequestBody Clothes clothes) {
        return repository.save(clothes);
    }


    @PutMapping("/products/clothes/{productNumber}")
    Clothes replaceClothes(@RequestBody Clothes newClothes, @PathVariable String productNumber) {
        Clothes oldClothes = repository.findByProductNumber(productNumber);
        oldClothes.setPrice(newClothes.getPrice());
        oldClothes.setDescription(newClothes.getDescription());
        oldClothes.setColor(newClothes.getColor());
        oldClothes.setEcology(newClothes.getEcology());
        oldClothes.setSize(newClothes.getSize());
        oldClothes.setName(newClothes.getName());
        oldClothes.setStock(newClothes.getStock());
        oldClothes.setImage(newClothes.getImage());
        return repository.save(oldClothes);
    }

    @DeleteMapping("/products/clothes/{productNumber}")
    String deleteClothes(@PathVariable String productNumber) {
        repository.deleteByProductNumber(productNumber);
        return "Successfully deleted";
    }
}
