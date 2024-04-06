package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Clothes;
import com.kotlinkoalas.koalamarket.repo.ClothesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClothesController {

    private final ClothesRepository repository;

    ClothesController(ClothesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/persons")
    List<Clothes> all() {
        return repository.findAll();
    }

//    @GetMapping("/persons/{id}")
//    Clothes one(@PathVariable Long id) {
//    }

    @PostMapping("/product/clothes")
    public Clothes newClothes(@RequestBody Clothes clothes) {
        return repository.save(clothes);
    }


    @PutMapping("/product/clothes/{id}")
    Clothes replacePerson(@RequestBody Clothes newClothes, @PathVariable Long id) {

        return repository.findById(id)
                .map(clothes -> {
                    clothes.setName(newClothes.getName());
                    return repository.save(clothes);
                })
                .orElseGet(() -> {
                    newClothes.setProductNumber(String.valueOf(id));
                    return repository.save(newClothes);
                });
    }

    @DeleteMapping("/product/clothes/{id}")
    String deleteClothes(@PathVariable Long id) {
        repository.deleteById(id);
        return "Successfully deleted";
    }
}
