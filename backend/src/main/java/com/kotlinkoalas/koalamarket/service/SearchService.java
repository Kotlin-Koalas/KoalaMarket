package com.kotlinkoalas.koalamarket.service;

import com.kotlinkoalas.koalamarket.model.Search;
import com.kotlinkoalas.koalamarket.repo.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private final SearchRepository repository;

    public SearchService(SearchRepository repository) {
        this.repository = repository;
    }

    public List<String> searchProducts(String dni) {
        List<String> response = new ArrayList<>();
        List<Search> searches = repository.findAllByDni(dni);
        for (Search search : searches) {
            response.add(search.getSearch());
        }
        return response;
    }

    public void addSearch(String dni, String search) {
        Search newSearch = new Search();
        newSearch.setDni(dni);
        newSearch.setSearch(search);
        repository.save(newSearch);
    }
}

