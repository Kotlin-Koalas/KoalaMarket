package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Clothes;
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


    @PutMapping("/products/clothes/{id}")
    Clothes replaceClothes(@RequestBody Clothes newClothes, @PathVariable String id) {

        return repository.findById(id)
                .map(Clothes -> {
                    Clothes.setName(Clothes.getName());
                    return repository.save(Clothes);
                })
                .orElseGet(() -> {
                    newClothes.setProductNumber(id);
                    return repository.save(newClothes);
                });
    }

    @DeleteMapping("/products/clothes/{id}")
    String deleteClothes(@PathVariable String id) {
        repository.deleteById(id);
        return "Successfully deleted";
    }
}
