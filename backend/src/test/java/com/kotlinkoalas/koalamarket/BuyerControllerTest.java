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

    Buyer buyer = new Buyer();

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        String dni = createRandomDni();
        buyer.setDni(dni);
        buyer.setName("Spring");
        buyer.setSurname("Boot");
        buyer.setUserID(dni);
        buyer.setEmail(dni + "@test.com");
        buyer.setPassword(dni);
        buyer.setBizum("123456789");
        List<Address> shippingAddresses = new ArrayList<>();
        shippingAddresses.add(new Address("Calle Falsa"));
        buyer.setShippingAddresses(shippingAddresses);
        buyer.setBillingAddresses(shippingAddresses);
    }

    @Test
    @Order(1)
    public void testRegister() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("dni", buyer.getDni());
        payload.put("name", buyer.getName());
        payload.put("surname", buyer.getSurname());
        payload.put("userID", buyer.getDni());
        payload.put("email", buyer.getEmail());
        payload.put("password", buyer.getPassword());
        payload.put("bizum", buyer.getBizum());
        payload.put("shippingAddress", "Calle Falsa");
        payload.put("billingAddress", "Calle Falsa");

        when(buyerRepository.findByDni(buyer.getDni())).thenReturn(null);

        ResponseEntity<String> response = buyerController.register(payload);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @Order(2)
    public void testLogin() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "spring@gmail.com");
        payload.put("password", "spring");

        when(buyerRepository.findByEmail(buyer.getEmail())).thenReturn(buyer);

        Buyer responseBuyer = buyerController.login(payload);
        assertEquals(true, responseBuyer != null);
    }

}
