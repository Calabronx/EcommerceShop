package com.factorit.EcommerceShop.service;

import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.repository.ProductRepository;
import com.factorit.EcommerceShop.repository.ShoppingCartRepository;
import com.factorit.EcommerceShop.utils.ScriptSqlRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    public ProductService(ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository) {
        this.productRepository = productRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

//    public ResponseEntity<?> addProduct(@RequestBody Product product) {
//        productRepository.save(product);
//        return new ResponseEntity<>("Producto agredado al carrito", HttpStatus.OK);
//
//    }

    public ResponseEntity<?> deleteProduct(Long id) {
        Optional<Product> productAux = productRepository.findById(id);
        if (productAux.isEmpty()) {
            return new ResponseEntity<>("El producto no existe en el carrito", HttpStatus.NOT_FOUND);
        }
        Long id_cart = productAux.get().getShoppingCart().getId();
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id_cart);
        int products_count = shoppingCart.getBuyCount();
        products_count--;
        shoppingCart.setBuyCount(products_count);

        productRepository.deleteById(id);
        shoppingCartRepository.save(shoppingCart);
        ScriptSqlRunner.runScript("src/main/resources/scripts/resetIdScript.sql");
        return new ResponseEntity<>("Producto eliminado con exito", HttpStatus.OK);

    }
}
