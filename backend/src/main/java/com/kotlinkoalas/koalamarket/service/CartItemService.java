package com.kotlinkoalas.koalamarket.service;

import com.kotlinkoalas.koalamarket.model.*;
import com.kotlinkoalas.koalamarket.model.products.Clothes;
import com.kotlinkoalas.koalamarket.model.products.Food;
import com.kotlinkoalas.koalamarket.model.products.Technology;
import com.kotlinkoalas.koalamarket.model.products.Toy;
import com.kotlinkoalas.koalamarket.repo.CartItemRepository;
import com.kotlinkoalas.koalamarket.service.products.ClothesService;
import com.kotlinkoalas.koalamarket.service.products.FoodService;
import com.kotlinkoalas.koalamarket.service.products.TechnologyService;
import com.kotlinkoalas.koalamarket.service.products.ToyService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartItemService {

    private final CartItemRepository repository;

    private final ClothesService clothesService;

    private final FoodService foodService;

    private final TechnologyService technologyService;

    private final ToyService toyService;

    public CartItemService(CartItemRepository repository, ClothesService clothesService, FoodService foodService, TechnologyService technologyService, ToyService toyService) {
        this.repository = repository;
        this.clothesService = clothesService;
        this.foodService = foodService;
        this.technologyService = technologyService;
        this.toyService = toyService;
    }

    public ResponseEntity<Map<String,Object>> getAllItemsInCart(String clientId){
        List<CartItem> items = repository.findAllByBuyerId(clientId);
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> itemsList = new ArrayList<>();
        if(items.isEmpty()){
            response.put("items", itemsList);
            return ResponseEntity.ok(response);
        }



        for (CartItem item : items) {
            String productNumber = item.getProductNumber();
            String cif = item.getCif();
            String category = item.getCategory();
            int quantity = item.getQuantity();
            Map<String, Object> itemDetails = new HashMap<>();
            switch (category){
                case "clothes":
                    Clothes clothes = clothesService.getClothesByProductNumber(productNumber, cif);
                    itemDetails.put("name", clothes.getName());
                    itemDetails.put("price", clothes.getPrice());
                    itemDetails.put("description", clothes.getDescription());
                    itemDetails.put("ecology", clothes.getEcology());
                    itemDetails.put("stock", clothes.getStock());
                    itemDetails.put("image", clothes.getImage());
                    itemDetails.put("color", clothes.getColor());
                    itemDetails.put("size", clothes.getSize());
                    itemDetails.put("category", clothes.getCategory());
                    break;
                case "food":
                    Food food = foodService.getFoodByProductNumber(productNumber, cif);
                    itemDetails.put("name", food.getName());
                    itemDetails.put("price", food.getPrice());
                    itemDetails.put("description", food.getDescription());
                    itemDetails.put("ecology", food.getEcology());
                    itemDetails.put("stock", food.getStock());
                    itemDetails.put("image", food.getImage());
                    itemDetails.put("calories", food.getCalories());
                    itemDetails.put("macros", food.getMacros());
                    itemDetails.put("category", food.getCategory());
                    break;
                case "technology":
                    Technology technology = technologyService.getTechnologyByProductNumber(productNumber, cif);
                    itemDetails.put("name", technology.getName());
                    itemDetails.put("price", technology.getPrice());
                    itemDetails.put("description", technology.getDescription());
                    itemDetails.put("ecology", technology.getEcology());
                    itemDetails.put("stock", technology.getStock());
                    itemDetails.put("image", technology.getImage());
                    itemDetails.put("electricConsumption", technology.getElectricConsumption());
                    itemDetails.put("brand", technology.getBrand());
                    itemDetails.put("category", technology.getCategory());
                    break;
                case "toy":
                    Toy toy = toyService.getToyByProductNumber(productNumber, cif);
                    itemDetails.put("name", toy.getName());
                    itemDetails.put("price", toy.getPrice());
                    itemDetails.put("description", toy.getDescription());
                    itemDetails.put("ecology", toy.getEcology());
                    itemDetails.put("stock", toy.getStock());
                    itemDetails.put("image", toy.getImage());
                    itemDetails.put("material", toy.getMaterial());
                    itemDetails.put("age", toy.getAge());
                    itemDetails.put("category", toy.getCategory());
                    break;
            }
            itemDetails.put("quantity", quantity);
            itemsList.add(itemDetails);
        }
        response.put("items", itemsList);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> addItemToCart(String id, String productNumber, String cif, String category, int quantity) {

        repository.save(new CartItem(productNumber, cif, category, quantity, id));
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body("Item added to cart");
    }

    public ResponseEntity<String> updateItemInCart(String id, String productNumber, String cif, String category, int quantity) {
        repository.findAllByBuyerId(id).forEach(item -> {
            if (item.getProductNumber().equals(productNumber) && item.getCif().equals(cif) && item.getCategory().equals(category)) {
                item.setQuantity(quantity);
                repository.save(item);
            }
        });
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body("Item updated in cart");
    }

    public ResponseEntity<String> deleteItemFromCart(String id, String productNumber, String cif, String category) {
        repository.findAllByBuyerId(id).forEach(item -> {
            if (item.getProductNumber().equals(productNumber) && item.getCif().equals(cif) && item.getCategory().equals(category)) {
                repository.delete(item);
            }
        });
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body("Item deleted from cart");
    }
}
