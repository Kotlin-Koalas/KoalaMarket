package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class WishListController {
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("/buyers/{id}/wishlist")
    public ResponseEntity<Map<String, Object>> getWishList(@PathVariable String id) {
        return wishListService.getAllItemsInWishList(id);
    }

    @PostMapping("/buyers/{id}/wishlist")
    public ResponseEntity<String> addItemToWishList(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        String productNumber = (String) payload.get("productNumber");
        String cif = (String) payload.get("cif");
        String category = (String) payload.get("category");
        return wishListService.addItemToWishList(id, productNumber, cif, category);
    }

    @DeleteMapping("/buyers/{id}/wishlist")
    public ResponseEntity<String> deleteItemFromWishList(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        String productNumber = (String) payload.get("productNumber");
        String cif = (String) payload.get("cif");
        String category = (String) payload.get("category");
        return wishListService.deleteItemFromWishList(id, productNumber, cif, category);
    }
}
