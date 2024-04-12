package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.factory.ProductFactory;
import com.kotlinkoalas.koalamarket.factory.ToyFactory;
import com.kotlinkoalas.koalamarket.model.Toy;
import com.kotlinkoalas.koalamarket.repo.ToyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ToyController {

    private final ToyRepository repository;

    ToyController(ToyRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products/toys")
    List<Toy> all() {
        return repository.findAll();
    }

    @PostMapping("/products/toys")
    public Toy newToy(@RequestBody Map<String, Object> payload) {
        String productNumber = (String) payload.get("productNumber");
        String name = (String) payload.get("name");
        double price = (double) payload.get("price");
        String description = (String) payload.get("description");
        String ecology = (String) payload.get("ecology");
        int stock = (int) payload.get("stock");
        String image = (String) payload.get("image");
        String cif = (String) payload.get("cif");

        String electricConsumption = (String) payload.get("electricConsumption");
        String brand = (String) payload.get("brand");

        ProductFactory ToyFactory = new ToyFactory();
        Toy technology = (Toy) ToyFactory.createProduct(productNumber, name, price, description, ecology, stock, image, cif, electricConsumption, brand);

        return repository.save(technology);
    }


    @PutMapping("/products/toys/{productNumber}")
    Toy replaceToy(@RequestBody Map<String, Object> payload, @PathVariable String productNumber) {
        Toy oldToy = repository.findByProductNumber(productNumber);
        oldToy.setPrice((double) payload.get("price"));
        oldToy.setDescription((String) payload.get("description"));
        oldToy.setEcology((String) payload.get("ecology"));
        oldToy.setName((String) payload.get("name"));
        oldToy.setStock((int) payload.get("stock"));
        oldToy.setImage((String) payload.get("image"));

        oldToy.setMaterial((String) payload.get("material"));
        oldToy.setAge((String) payload.get("age"));

        return repository.save(oldToy);
    }

    @DeleteMapping("/products/toys/{productNumber}")
    String deleteToy(@PathVariable String productNumber) {
        repository.deleteByProductNumber(productNumber);
        return "Successfully deleted";
    }
}