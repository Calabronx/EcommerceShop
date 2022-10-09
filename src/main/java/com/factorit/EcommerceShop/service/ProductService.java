package com.factorit.EcommerceShop.service;

import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.repository.ProductRepository;
import com.factorit.EcommerceShop.repository.ShoppingCartRepository;
import com.factorit.EcommerceShop.utils.ScriptSqlRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        Product product = productRepository.getById(id);
        ShoppingCart shoppingCart = shoppingCartRepository.getById(product.getShoppingCart().getId());
        int products_count = shoppingCart.getBuyCount();
        double rest_amount = product.getPrice().doubleValue();
        products_count--;
        BigDecimal refresh_amount = BigDecimal.valueOf(shoppingCart.getTotalAmount().doubleValue() - rest_amount);
        shoppingCart.setTotalAmount(refresh_amount);
        shoppingCart.setBuyCount(products_count);

        productRepository.deleteById(id);
        shoppingCartRepository.save(shoppingCart);
        ScriptSqlRunner.runScript("src/main/resources/scripts/resetIdScript.sql");
        return new ResponseEntity<>("Producto eliminado con exito", HttpStatus.OK);

    }

    public ResponseEntity<?> addQuantity(Long id) {
        Optional<Product> productAux = productRepository.findById(id);
        //List<Product> productList = new ArrayList<>();
        if (productAux.isEmpty()) {
            return new ResponseEntity<>("Error producto no se a podido agregar", HttpStatus.CONFLICT);
        }
        Product product = productRepository.getById(id);
        ShoppingCart cart = shoppingCartRepository.getById(product.getShoppingCart().getId());
        int quantity_product = Integer.parseInt(product.getQuantity());
        int increment_cart = cart.getBuyCount();
        quantity_product++;
        increment_cart++;
        cart.setBuyCount(increment_cart);
        product.setQuantity(String.valueOf(quantity_product));
        productRepository.save(product);

        return new ResponseEntity<>("Se agrego otro producto mas de :" + product.getName(), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteQuantity(Long id) {
        Optional<Product> productAux = productRepository.findById(id);
        //List<Product> productList = new ArrayList<>();
        if (productAux.isEmpty()) {
            return new ResponseEntity<>("Error producto no se a podido agregar", HttpStatus.CONFLICT);
        }
        Product product = productRepository.getById(id);
        ShoppingCart shoppingCart = shoppingCartRepository.getById(product.getShoppingCart().getId());
        int quantity_product = Integer.parseInt(product.getQuantity());
        int decrement_cart = shoppingCart.getBuyCount();
        decrement_cart--;
        quantity_product--;
        shoppingCart.setBuyCount(decrement_cart);
        product.setQuantity(String.valueOf(quantity_product));
        productRepository.save(product);
        if (shoppingCart.getBuyCount() <= 0) {
            shoppingCart.setBuyCount(0);
            shoppingCartRepository.save(shoppingCart);
        }
        if (product.getQuantity().equals("0")) {
            productRepository.deleteById(id);
            return new ResponseEntity<>("Se elimino el producto :", HttpStatus.OK);
        }
        return new ResponseEntity<>("Se elimino una cantidad de :" + product.getName(), HttpStatus.OK);
    }
}

