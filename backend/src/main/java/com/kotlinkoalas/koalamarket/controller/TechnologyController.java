package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.factory.FoodFactory;
import com.kotlinkoalas.koalamarket.factory.ProductFactory;
import com.kotlinkoalas.koalamarket.factory.TechnologyFactory;
import com.kotlinkoalas.koalamarket.model.Food;
import com.kotlinkoalas.koalamarket.model.Technology;
import com.kotlinkoalas.koalamarket.repo.FoodRepository;

import com.kotlinkoalas.koalamarket.service.TechnologyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TechnologyController {

    private final TechnologyService technologyService;

    TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @GetMapping("/products/technology")
    List<Technology> all() {
        return technologyService.getAllTechnologies();
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

        return technologyService.createTechnology(productNumber,name,price,description,ecology,stock,image,cif,electricConsumption,brand);
    }


    @PutMapping("/products/technology/{productNumber}")
    Technology replaceTechnology(@RequestBody Map<String, Object> payload, @PathVariable String productNumber) {
        String stringPrice = (String) payload.get("price");
        double price = Double.parseDouble(stringPrice);
        String description = (String) payload.get("description");
        String ecology = (String) payload.get("ecology");
        String name = (String) payload.get("name");
        int stock = (int) payload.get("stock");
        String image = (String) payload.get("image");

        String brand = (String) payload.get("brand");
        String electricConsumption = (String) payload.get("electricConsumption");

        return technologyService.updateTechnology(productNumber,name,price,description,ecology,stock,image,brand,electricConsumption);
    }

    @DeleteMapping("/products/technology/{productNumber}")
    String deleteTechnology(@PathVariable String productNumber) {
        technologyService.deleteTechnology(productNumber);
        return "Successfully deleted";
    }
}
