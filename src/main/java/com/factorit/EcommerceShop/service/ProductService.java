package com.factorit.EcommerceShop.service;

import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    public ResponseEntity<?> addProduct(@RequestBody Product product) {
//        productRepository.save(product);
//        return new ResponseEntity<>("Producto agredado al carrito", HttpStatus.OK);
//
//    }
}
