package com.kotlinkoalas.koalamarket.service.products;

import com.kotlinkoalas.koalamarket.model.products.SatisfactionUser;
import com.kotlinkoalas.koalamarket.repo.products.SatisfactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class SatisfactionService {
    private final SatisfactionRepository repository;

    public SatisfactionService(SatisfactionRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<String> addSatisfaction(String productNumber, String dni, double satisfaction) {
        SatisfactionUser newSatisfaction = new SatisfactionUser();
        newSatisfaction.setProductNumber(productNumber);
        newSatisfaction.setDni(dni);
        newSatisfaction.setGradeSatisfaction(satisfaction);
        repository.save(newSatisfaction);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body("Satisfaction added successfully");

    }

    public ResponseEntity<String> updateSatisfaction(String productNumber, String dni, double satisfaction) {
        SatisfactionUser currentSatisfaction = repository.findByProductNumberAndDni(productNumber, dni);
        currentSatisfaction.setGradeSatisfaction(satisfaction);
        repository.save(currentSatisfaction);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body("Satisfaction updated successfully");
    }

    public double getSatisfactionMean(String productNumber){
        List<SatisfactionUser> satisfactions = repository.findAllByProductNumber(productNumber);
        if (satisfactions.isEmpty()){
            return 0;
        }
        double sum = 0;
        for(SatisfactionUser satisfaction : satisfactions){
            sum += satisfaction.getGradeSatisfaction();
        }
        return Double.parseDouble(String.format(Locale.US ,"%.1f", sum / satisfactions.size()));
    }
}
