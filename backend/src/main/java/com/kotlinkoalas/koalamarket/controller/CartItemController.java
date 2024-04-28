package com.kotlinkoalas.koalamarket.controller;


import com.kotlinkoalas.koalamarket.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CartItemController {
    private final CartItemService cartItemService;

    CartItemController(CartItemService shoppingCartService) {
        this.cartItemService = shoppingCartService;
    }

    @GetMapping("/buyers/{id}/cart")
    public ResponseEntity<Map<String,Object>> getCart(@PathVariable String id) {
        return cartItemService.getAllItemsInCart(id);
    }

    @PostMapping("/buyers/{id}/cart")
    public ResponseEntity<String> addItemToCart(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        String productNumber = (String) payload.get("productNumber");
        String cif = (String) payload.get("cif");
        String category = (String) payload.get("category");
        int quantity = (int) payload.get("quantity");
        return cartItemService.addItemToCart(id, productNumber, cif, category, quantity);
    }

    @PutMapping("/buyers/{id}/cart")
    public ResponseEntity<String> updateItemInCart(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        String productNumber = (String) payload.get("productNumber");
        String cif = (String) payload.get("cif");
        String category = (String) payload.get("category");
        int quantity = (int) payload.get("quantity");
        return cartItemService.updateItemInCart(id, productNumber, cif, category, quantity);
    }

    @DeleteMapping("/buyers/{id}/cart")
    public ResponseEntity<String> deleteItemFromCart(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        String productNumber = (String) payload.get("productNumber");
        String cif = (String) payload.get("cif");
        String category = (String) payload.get("category");
        return cartItemService.deleteItemFromCart(id, productNumber, cif, category);
    }
}
