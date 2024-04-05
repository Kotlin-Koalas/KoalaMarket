package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Clothes;
import com.kotlinkoalas.koalamarket.repo.ClothesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClothesController {

    private final ClothesRepository  repository;

    ClothesController(ClothesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/clothes")
    List<Clothes> all() {
        return repository.findAll();
    }

//    @GetMapping("/persons/{id}")
//    Person one(@PathVariable Long id) {
//    }

    @PostMapping("/clothes")
    public Clothes newCloth(@RequestBody Clothes cloth) {
        return repository.save(cloth);
    }


    @PutMapping("/clothes/{id}")
    Clothes replacePerson(@RequestBody Clothes newCloth, @PathVariable String id) {

        return repository.findById(id)
                .map(cloth -> {
                    cloth.setName(newCloth.getName());
                    return repository.save(cloth);
                })
                .orElseGet(() -> {
                    newCloth.setProductNumber(id);
                    return repository.save(newCloth);
                });
    }

    @DeleteMapping("/clothes/{id}")
    String deletePerson(@PathVariable String id) {
        repository.deleteById(id);
        return "Successfully deleted";
    }
}
