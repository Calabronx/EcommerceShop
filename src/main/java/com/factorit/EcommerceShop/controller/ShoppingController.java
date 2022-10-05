package com.factorit.EcommerceShop.controller;

import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ShoppingController {

    @Autowired
    private ShoppingCartService cartService;

    @PostMapping("/create")
    public ResponseEntity<?> createCart(@RequestBody ShoppingCart cart) {
        return cartService.createNewCart(cart);
    }

    @GetMapping("/cartstatus/{id}")
    public ResponseEntity<?> getCartState(@PathVariable Long id) {
        return cartService.findCart(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        return cartService.deleteCart(id);
    }

    @PostMapping("/addproduct/{id}")
    public ResponseEntity<?> addToCart(@RequestBody Product product, @PathVariable Long id) {
        return cartService.addProduct(product,id);
    }

}
