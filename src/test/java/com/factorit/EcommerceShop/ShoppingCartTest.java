package com.factorit.EcommerceShop;

import com.factorit.EcommerceShop.controller.ShoppingController;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.repository.ShoppingCartRepository;
import com.factorit.EcommerceShop.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = EcommerceShopApplication.class)
@AutoConfigureMockMvc
@Service
public class ShoppingCartTest {

    @Autowired
    public MockMvc mockMvc;

    @InjectMocks
    ShoppingCartRepository shoppingCartRepository = mock(ShoppingCartRepository.class);

    @InjectMocks
    private ShoppingController controller = mock(ShoppingController.class);

    @MockBean
    private ShoppingCartService shoppingCartService = mock(ShoppingCartService.class);

    @Test
    public void shouldReturnCartsList() throws Exception {

        List<ShoppingCart> listCarts = shoppingCartRepository.findAll();
        int size = listCarts.size();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(
                listCarts,
                header,
                HttpStatus.OK
        );

        when(shoppingCartRepository.findAll()).thenReturn(listCarts);
    }

    @Test
    public void shouldReturnCart() throws Exception {
        Long id = 1L;
        ShoppingCart cart = shoppingCartRepository.getById(id);
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(
                cart,
                header,
                HttpStatus.OK
        );

        //when(shoppingCartService.getAllCarts()).thenReturn(responseEntity.toString());
        when(shoppingCartRepository.findById(id)).thenReturn(cartAux);
    }
}