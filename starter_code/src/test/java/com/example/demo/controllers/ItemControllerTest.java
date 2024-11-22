package com.example.demo.controllers;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

public class ItemControllerTest {
    private ItemController itemController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "userRepository", userRepo);
        TestUtils.injectObjects(itemController, "cartRepository", cartRepo);
        TestUtils.injectObjects(itemController, "bCryptPasswordEncoder", encoder);
    }

}
