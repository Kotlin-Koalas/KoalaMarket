package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.factory.FoodFactory;
import com.kotlinkoalas.koalamarket.factory.ProductFactory;
import com.kotlinkoalas.koalamarket.factory.TechnologyFactory;
import com.kotlinkoalas.koalamarket.model.Food;
import com.kotlinkoalas.koalamarket.model.Technology;
import com.kotlinkoalas.koalamarket.repo.FoodRepository;
import com.kotlinkoalas.koalamarket.repo.TechnologyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/products/technology")
    public Technology newTechnology(@RequestBody Map<String, Object> payload) {
        String productNumber = (String) payload.get("productNumber");
        String name = (String) payload.get("name");
        String stringPrice = (String) payload.get("price");
        double price = Double.parseDouble(stringPrice) ;
        String description = (String) payload.get("description");
        String ecology = (String) payload.get("ecology");
        int stock = (int) payload.get("stock");
        String image = (String) payload.get("image");
        String cif = (String) payload.get("cif");

        String electricConsumption = (String) payload.get("electricConsumption");
        String brand = (String) payload.get("brand");

        if (repository.existsByProductNumberAndCif(productNumber, cif)) {
            throw new RuntimeException("Product number and cif already exists");
        }

        ProductFactory TechnologyFactory = new TechnologyFactory();
        Technology technology = (Technology) TechnologyFactory.createProduct(productNumber, name, price, description, ecology, stock, image, cif, electricConsumption, brand);

        return repository.save(technology);
    }


    @PutMapping("/products/technology/{productNumber}")
    Technology replaceTechnology(@RequestBody Map<String, Object> payload, @PathVariable String productNumber) {
        Technology oldTechnology = repository.findByProductNumber(productNumber);
        String stringPrice = (String) payload.get("price");
        oldTechnology.setPrice(Double.parseDouble(stringPrice));
        oldTechnology.setDescription((String) payload.get("description"));
        oldTechnology.setElectricConsumption((String) payload.get("electricConsumption"));
        oldTechnology.setEcology((String) payload.get("ecology"));
        oldTechnology.setBrand((String) payload.get("brand"));
        oldTechnology.setName((String) payload.get("name"));
        oldTechnology.setStock((int) payload.get("stock"));
        oldTechnology.setImage((String) payload.get("image"));
        return repository.save(oldTechnology);
    }

    @DeleteMapping("/products/technology/{productNumber}")
    String deleteTechnology(@PathVariable String productNumber) {
        repository.deleteByProductNumber(productNumber);
        return "Successfully deleted";
    }
}
