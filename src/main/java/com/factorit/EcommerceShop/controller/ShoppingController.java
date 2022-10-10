package com.factorit.EcommerceShop.controller;

import com.factorit.EcommerceShop.model.Client;
import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.service.ShoppingCartService;
import com.factorit.EcommerceShop.utils.SystemTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ShoppingController {

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private SystemTime systemTime;

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

//    @PostMapping("/addproduct/{id}")
//    public ResponseEntity<?> addToCart(@RequestBody Product product, @PathVariable Long id) {
//        return cartService.addProduct(product, id);
//    }

    @PostMapping("/addproduct/{id}")
    public ResponseEntity<?> add(@RequestBody Product product, @PathVariable Long id) {
        return cartService.addProductFromMarket(product, id);
    }

    @PostMapping("/buy/{id}")
    public ResponseEntity<?> buyCart(@RequestBody Client client, @PathVariable Long id) {
        return cartService.buyShoppingCart(id, client);
    }

    @RequestMapping("/time")
    public void time() {
        systemTime.deleteInactiveCarts();
    }

    @GetMapping("/getallcarts")
    public ResponseEntity<?> getAllShoppingCarts() {
        return cartService.getAllCarts();
    }

    @PostMapping("/add-name/{id}")
    public ResponseEntity<?> addProductByName(@RequestBody Product product,@PathVariable Long id) {
        return cartService.addProductByName(product,id);
    }
}
