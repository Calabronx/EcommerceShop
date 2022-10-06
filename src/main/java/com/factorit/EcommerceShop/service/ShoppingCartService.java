package com.factorit.EcommerceShop.service;

import com.factorit.EcommerceShop.model.Client;
import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.repository.ProductRepository;
import com.factorit.EcommerceShop.repository.ShoppingCartRepository;
import com.factorit.EcommerceShop.utils.CartEnum;
import com.factorit.EcommerceShop.utils.ScriptSqlRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final ProductRepository productRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }


    public ResponseEntity<?> createNewCart(@Valid ShoppingCart cart) {
        if (cart.getCartName().equals(String.valueOf(CartEnum.COMUN)) || cart.getCartName().equals(String.valueOf(CartEnum.PROMOCIONABLE))
                || cart.getCartName().equals(String.valueOf(CartEnum.VIP))) {
            cart.setCreatedAt(cart.getCreatedAt());
            shoppingCartRepository.save(cart);
            ScriptSqlRunner.runScript("src/main/resources/scripts/resetIdScript.sql");
        } else {
            return new ResponseEntity<>("Tipo de carrito no es correcto", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>("Carrito de compras  con id: " + cart.getId() + " de tipo: " + cart.getCartName() + " ha sido creado con exito!", HttpStatus.OK);
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
        ScriptSqlRunner.runScript("src/main/resources/scripts/resetIdScript.sql");
        return new ResponseEntity<>("Carrito eliminado con exito", HttpStatus.OK);
    }

    public ResponseEntity<?> addProduct(@RequestBody Product product, Long id) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        if (cartAux.isEmpty()) {
            return new ResponseEntity<>("El carrito buscado es inexistente", HttpStatus.NOT_FOUND);
        }
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        shoppingCart.setBuyCount(incrementCart(shoppingCart));
        shoppingCart.addProduct(product);
        product.setShoppingCart(shoppingCart);

        double totalAmount = 0;
        for (int i = 0; i < shoppingCart.getProductsList().size(); i++) {
            double value = shoppingCart.getProductsList().get(i).getPrice().doubleValue();
            totalAmount += value;
            BigDecimal decimal = BigDecimal.valueOf(totalAmount);
            shoppingCart.setTotalAmount(decimal);
        }

        shoppingCartRepository.save(shoppingCart);
        return new ResponseEntity<>("Producto agredado al carrito: " + shoppingCart.getProductsList(), HttpStatus.OK);
    }

    /**
     *
     * agregar atributo a ShoppingCart de precioInicial, para guardar el precio anterior al descuento
     * asi el usuario puede ver el precio anterior y el precio final con el descuento
     * @return
     */
    public ResponseEntity<?> buyShoppingCart(Long id, Client client) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        if (cartAux.isEmpty()) {
            return new ResponseEntity<>("El carrito que quiere comprar es inexistente", HttpStatus.NOT_FOUND);
        }
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        shoppingCart.setHasBought(true);
        shoppingCart.setClientName(client.getName());
        /**
         * cambiar por una implementacion mas ordenada, que sea una sola funcion y le pase los parametos dependiendo el caso
         */
        if (shoppingCart.getBuyCount() == 4) {
            double totalAmount = shoppingCart.getTotalAmount().doubleValue();
            calculateFinalPrice(totalAmount, 25, shoppingCart);
        } else if (shoppingCart.getBuyCount() > 10 && shoppingCart.getCartName().equals(String.valueOf(CartEnum.COMUN))) {
            double totalAmount = shoppingCart.getTotalAmount().doubleValue();
            calculateFinalPrice(totalAmount, 100, shoppingCart);
        } else if (shoppingCart.getBuyCount() > 10 && shoppingCart.getCartName().equals(String.valueOf(CartEnum.PROMOCIONABLE))) {
            double totalAmount = shoppingCart.getTotalAmount().doubleValue();
            calculateFinalPrice(totalAmount, 300, shoppingCart);
            //faltaria que este ultimo caso bonifique el producto mas barato
        } else if (shoppingCart.getBuyCount() > 10 && shoppingCart.getCartName().equals(String.valueOf(CartEnum.VIP))) {
            double totalAmount = shoppingCart.getTotalAmount().doubleValue();
            double finalPrice = calculateFinalPrice(totalAmount, 500, shoppingCart);
            shoppingCart.setTotalAmount(BigDecimal.valueOf(finalPrice));
        }
        shoppingCartRepository.save(shoppingCart);

        return new ResponseEntity<>("Compra realizada con exito del carrito: " + shoppingCart +
                " precio total de la compra: $" + shoppingCart.getTotalAmount(), HttpStatus.OK);
    }

    public double calculateFinalPrice(double initialAmount, int discount, ShoppingCart shoppingCart) {
        double finalPrice = (initialAmount * discount) / 100;
        BigDecimal decimal = BigDecimal.valueOf(finalPrice);
        shoppingCart.setTotalAmount(decimal);
        return finalPrice;
    }


    public int incrementCart(ShoppingCart shoppingCart) {
        int cart_products = shoppingCart.getBuyCount();
        cart_products++;
        return cart_products;
    }

}
