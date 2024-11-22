package com.example.demo.controllers;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.TestUtils;
import com.example.demo.TestUtils.LoginRequest;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

public class UserControllerTest {
    private UserController userController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepo);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void create_user_happy_path() throws Exception {
        CreateUserRequest req = new CreateUserRequest();
        req.setUsername("test");
        req.setPassword("testPassword");
        req.setConfirmPassword("testPassword");

        final ResponseEntity<User> response = userController.createUser(req);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertNull(u.getPassword()); // response doesn't include the password
    }

    @Test
    public void create_user_short_password() throws Exception {
        CreateUserRequest req = new CreateUserRequest();
        req.setUsername("test");
        req.setPassword("test");
        req.setConfirmPassword("test");

        final ResponseEntity<User> response = userController.createUser(req);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

        User u = response.getBody();
        assertNull(u);
    }

    @Test
    public void create_user_passwords_dont_match() throws Exception {
        CreateUserRequest req = new CreateUserRequest();
        req.setUsername("test");
        req.setPassword("testPassword");
        req.setConfirmPassword("passwordTest");

        final ResponseEntity<User> response = userController.createUser(req);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

        User u = response.getBody();
        assertNull(u);
    }
}
