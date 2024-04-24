package com.kotlinkoalas.koalamarket.controller;


import com.kotlinkoalas.koalamarket.model.CartItem;
import com.kotlinkoalas.koalamarket.service.ShoppingCartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/buyers/{id}/cart")
    public List<CartItem> getCart(@PathVariable String id) {
        return shoppingCartService.getAllItemsInCart(id);
    }

}
