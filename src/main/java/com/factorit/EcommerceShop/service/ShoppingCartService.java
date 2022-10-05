package com.factorit.EcommerceShop.service;

import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.repository.ShoppingCartRepository;
import com.factorit.EcommerceShop.utils.CartEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private static int product_count = 0;

    /**
     * dependency inj
     *
     * @param shoppingCartRepository
     */
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ResponseEntity<?> createNewCart(@Valid ShoppingCart cart) {
        if (cart.getCartName().equals(String.valueOf(CartEnum.COMUN)) || cart.getCartName().equals(String.valueOf(CartEnum.PROMOCIONABLE))
                || cart.getCartName().equals(String.valueOf(CartEnum.VIP))) {
            cart.setCreatedAt(cart.getCreatedAt());
            shoppingCartRepository.save(cart);
        } else {
            return new ResponseEntity<>("Tipo de carrito no es correcto", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>("Carrito de compras de tipo " + cart.getCartName() + " creado con exito!", HttpStatus.OK);
    }

    public ResponseEntity<?> findCart(Long id) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        List<ShoppingCart> cartList = new ArrayList<>();
        if (cartAux.isEmpty()) {
            return new ResponseEntity<>("El carrito que ha solicitado es inexistente", HttpStatus.NOT_FOUND);
        }
        cartList.add(cartAux.get());
        return new ResponseEntity<>("Estado de Carrito: " + cartList, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteCart(Long id) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        if (cartAux.isEmpty()) {
            return new ResponseEntity<>("El carrito que ha solicitado eliminar es inexistente", HttpStatus.NOT_FOUND);
        }
        shoppingCartRepository.deleteById(id);
        return new ResponseEntity<>("Carrito eliminado con exito", HttpStatus.OK);
    }

    public ResponseEntity<?> addProduct(@RequestBody Product product, Long id) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        if (cartAux.isEmpty()) {
            return new ResponseEntity<>("El carrito buscado es inexistente", HttpStatus.NOT_FOUND);
        }
        cartAux.get().addProduct(product);
        int count_cart = cartAux.get().getBuyCount();
        count_cart++;
        cartAux.get().setBuyCount(count_cart);
        product.setShoppingCart(cartAux.get());

        List<Product> pricesList = cartAux.get().getProductsList();
        double totalAmount = 0;

        for (int i = 0; i < pricesList.size(); i++) {
            double value = pricesList.get(i).getPrice().doubleValue();
            totalAmount += value;
            BigDecimal decimal = BigDecimal.valueOf(totalAmount);
            cartAux.get().setTotalAmount(decimal);
        }

        shoppingCartRepository.save(cartAux.get());
        return new ResponseEntity<>("Producto agredado al carrito: " + cartAux.get().getProductsList(), HttpStatus.OK);
    }
}
