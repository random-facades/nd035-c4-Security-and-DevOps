package com.example.demo.controllers;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

public class OrderControllerTest {
    private OrderController orderController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepo);
        TestUtils.injectObjects(orderController, "cartRepository", cartRepo);
        TestUtils.injectObjects(orderController, "bCryptPasswordEncoder", encoder);
    }

}
