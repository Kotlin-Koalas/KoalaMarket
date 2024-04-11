package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Buyer;
import com.kotlinkoalas.koalamarket.repo.BuyerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class BuyerController {

    private final BuyerRepository repository;

    BuyerController(BuyerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/buyers")
    List<Buyer> all() {
        return repository.findAll();
    }

    @PostMapping("/buyers")
    public Buyer newBuyer(@RequestBody Buyer buyer) {
        return repository.save(buyer);
    }

    @PutMapping("/buyers/{id}")
    Buyer replaceBuyer(@RequestBody Buyer newBuyer, @PathVariable String id) {
        return repository.findById(id)
                .map(buyer -> {
                    buyer.setDni(newBuyer.getDni());
                    return repository.save(buyer);
                })
                .orElseGet(() -> {
                    newBuyer.setDni(id);
                    return repository.save(newBuyer);
                });
    }

    @DeleteMapping("/buyers/{id}")
    String deleteBuyer(@PathVariable String id) {
        repository.deleteById(id);
        return "Successfully deleted";
    }

    @PostMapping("/buyers/login")
    public Buyer login(@RequestBody Buyer buyer) {
        Buyer existingBuyer = repository.findByDni(buyer.getDni());

        if (existingBuyer != null && Objects.equals(existingBuyer.getDni(), buyer.getDni())) {
            return existingBuyer;
        } else {
            return new Buyer();
        }
    }

    @PostMapping("/buyers/register")
    public ResponseEntity<String> register(@RequestBody Buyer newBuyer) {
        Buyer existingBuyer = repository.findByDni(newBuyer.getDni());

        if (existingBuyer != null) {
            return ResponseEntity.status(400).body("A buyer with the same DNI already exists");
        } else {
            repository.save(newBuyer);
            return ResponseEntity.ok("Registration successful");
        }
    }
}