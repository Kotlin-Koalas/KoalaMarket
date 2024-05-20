package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/buyers/{dni}/search")
    public List<String> searchProducts(@PathVariable String dni) {
        return searchService.searchProducts(dni);
    }

    @PostMapping("/buyers/{dni}/search")
    public ResponseEntity<String> addSearch(@PathVariable String dni, @RequestBody Map<String, Object> payload) {
        String search = (String) payload.get("search");
        return searchService.addSearch(dni, search);
    }
}
