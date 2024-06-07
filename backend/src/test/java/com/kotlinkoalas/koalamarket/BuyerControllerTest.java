package com.kotlinkoalas.koalamarket;

import com.kotlinkoalas.koalamarket.controller.BuyerController;
import com.kotlinkoalas.koalamarket.model.Address;
import com.kotlinkoalas.koalamarket.model.Buyer;
import com.kotlinkoalas.koalamarket.repo.BuyerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kotlinkoalas.koalamarket.utils.ClientHelper.createRandomDni;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BuyerControllerTest extends BaseTest{

    @InjectMocks
    BuyerController buyerController;

    @Mock
    BuyerRepository buyerRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegister() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("dni", "12345678A");
        payload.put("name", "Spring");
        payload.put("surname", "Boot");
        payload.put("userID", "springboot");
        payload.put("email", "spring@boot.com");
        payload.put("password", "SpringBoot777");
        payload.put("bizum", "123456789");
        payload.put("shippingAddress", "Calle Falsa");
        payload.put("billingAddress", "Calle Falsa");

        ResponseEntity<String> response = buyerController.register(payload);
        assertEquals(200, response.getStatusCode().value());

        Map<String, Object> payload2 = new HashMap<>();
        payload2.put("dni", 8);
        payload2.put("name", 2);
        payload2.put("surname", true);
        payload2.put("userID", 2.2);
        payload2.put("email", 0.3);
        payload2.put("password", 2);
        payload2.put("bizum", "a");
        payload2.put("shippingAddress", false);
        payload2.put("billingAddress", 1000);

        ResponseEntity<String> response2 = buyerController.register(payload2);
        assertEquals(422, response2.getStatusCode().value());
    }
}
