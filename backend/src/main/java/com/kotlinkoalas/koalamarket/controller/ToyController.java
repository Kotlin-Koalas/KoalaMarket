package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Toy;
import com.kotlinkoalas.koalamarket.service.products.ToyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ToyController {

    private final ToyService toyService;

    ToyController(ToyService toyService) {
        this.toyService = toyService;
    }

    @GetMapping("/products/toys")
    List<Toy> all() {
        return toyService.getAllToys();
    }

    @PostMapping("/products/toys")
    public Toy newToy(@RequestBody Map<String, Object> payload) {
        String productNumber = (String) payload.get("productNumber");
        String name = (String) payload.get("name");
        String stringPrice = (String) payload.get("price");
        double price = Double.parseDouble(stringPrice);
        String description = (String) payload.get("description");
        String ecology = (String) payload.get("ecology");
        int stock = (int) payload.get("stock");
        String image = (String) payload.get("image");
        String cif = (String) payload.get("cif");

        String age = (String) payload.get("age");
        String material = (String) payload.get("material");

        return toyService.createToy(productNumber,name,price,description,ecology,stock,image,cif,material,age);
    }


    @PutMapping("/products/toys/{productNumber}")
    Toy replaceToy(@RequestBody Map<String, Object> payload, @PathVariable String productNumber) {
        String stringPrice = (String) payload.get("price");
        double price = Double.parseDouble(stringPrice);
        String description = (String) payload.get("description");
        String ecology = (String) payload.get("ecology");
        String name = (String) payload.get("name");
        int stock = (int) payload.get("stock");
        String image = (String) payload.get("image");

        String material = (String) payload.get("material");
        String age = (String) payload.get("age");

        return toyService.updateToy(productNumber,name,price,description,ecology,stock,image,material,age);
    }

    @DeleteMapping("/products/toys/{productNumber}")
    String deleteToy(@PathVariable String productNumber) {
        toyService.deleteToy(productNumber);
        return "Successfully deleted";
    }
}