package com.factorit.EcommerceShop.controller;

import com.factorit.EcommerceShop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v2")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/add/quantity/{id}")
    public ResponseEntity<?> addQuantity(@PathVariable Long id) {
        return productService.addQuantity(id);
    }

    @DeleteMapping("/del/quantity/{id}")
    public ResponseEntity<?> deleteQuantity(@PathVariable Long id) {
        return productService.deleteQuantity(id);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllProducts() {
        return productService.getAllProducts();
    }
}
