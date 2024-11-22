package com.example.demo.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

public class CartControllerTest {
    private CartController cartController;
    private UserRepository userRepo = mock(UserRepository.class);
    private ItemRepository itemRepo = mock(ItemRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);

    @Before
    public void setup() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepo);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepo);
    }

    @Test
    public void addToCartTest()
    {
		String username = "CART_USER";
		String password = "CART_PASSWORD";
        User test = new User();
        test.setUsername(username);
        test.setPassword(password);
        test.setCart(new Cart());

        Item item = new Item();
        item.setId(1L);
        item.setName("RANDOM ITEM");
        item.setPrice(new BigDecimal(2000));

        ModifyCartRequest req = new ModifyCartRequest();
        req.setItemId(item.getId());
        req.setQuantity(1);
        req.setUsername(username);

        Optional<Item> opt = Optional.of(item);

        when(userRepo.findByUsername(anyString())).thenReturn(test);
        when(itemRepo.findById(anyLong())).thenReturn(opt);

        ResponseEntity<Cart> response = cartController.addTocart(req);

		assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(test.getCart(), response.getBody());
    }

    @Test
    public void removeFromCartTest()
    {
		String username = "CART_USER";
		String password = "CART_PASSWORD";
        User test = new User();
        test.setUsername(username);
        test.setPassword(password);
        test.setCart(new Cart());

        Item item = new Item();
        item.setId(1L);
        item.setName("RANDOM ITEM");
        item.setPrice(new BigDecimal(2000));

        ModifyCartRequest req = new ModifyCartRequest();
        req.setItemId(item.getId());
        req.setQuantity(2);
        req.setUsername(username);

        Optional<Item> opt = Optional.of(item);

        when(userRepo.findByUsername(anyString())).thenReturn(test);
        when(itemRepo.findById(anyLong())).thenReturn(opt);

        ResponseEntity<Cart> response = cartController.addTocart(req);

		assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(test.getCart(), response.getBody());

        req.setQuantity(1);

        response = cartController.removeFromcart(req);

		assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(test.getCart(), response.getBody());

        assertEquals(1, test.getCart().getItems().size());
    }
}
