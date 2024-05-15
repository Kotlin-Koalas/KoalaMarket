package com.kotlinkoalas.koalamarket.service.products;

import com.kotlinkoalas.koalamarket.model.products.SatisfactionUser;
import com.kotlinkoalas.koalamarket.repo.products.SatisfactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SatisfactionService {
    private final SatisfactionRepository repository;

    public SatisfactionService(SatisfactionRepository repository) {
        this.repository = repository;
    }

    public void addSatisfaction(String productNumber, String dni, float satisfaction) {
        SatisfactionUser newSatisfaction = new SatisfactionUser();
        newSatisfaction.setProductNumber(productNumber);
        newSatisfaction.setDni(dni);
        newSatisfaction.setGradeSatisfaction(satisfaction);
        repository.save(newSatisfaction);
    }

    public void updateSatisfaction(String productNumber, String dni, float satisfaction) {
        SatisfactionUser currentSatisfaction = repository.findByProductNumberAndDni(productNumber, dni);
        currentSatisfaction.setGradeSatisfaction(satisfaction);
        repository.save(currentSatisfaction);
    }

    public float getSatisfactionMean(String productNumber){
        List<SatisfactionUser> satisfactions = repository.findAllByProductNumber(productNumber);
        float sum = 0;
        for(SatisfactionUser satisfaction : satisfactions){
            sum += satisfaction.getGradeSatisfaction();
        }
        return sum/satisfactions.size();
    }
}
