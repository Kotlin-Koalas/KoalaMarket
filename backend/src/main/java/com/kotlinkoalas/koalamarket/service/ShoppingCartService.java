package com.kotlinkoalas.koalamarket.service;

import com.kotlinkoalas.koalamarket.model.CartItem;
import com.kotlinkoalas.koalamarket.model.Product;
import com.kotlinkoalas.koalamarket.model.ShoppingCart;
import com.kotlinkoalas.koalamarket.repo.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository repository;

    public ShoppingCartService(ShoppingCartRepository repository) {
        this.repository = repository;
    }

    public List<CartItem> getAllItemsInCart(String clientId){
        return repository.findByClientId(clientId).getCartItems();
    }
}
