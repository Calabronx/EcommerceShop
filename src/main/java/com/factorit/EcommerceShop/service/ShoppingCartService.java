package com.factorit.EcommerceShop.service;

import com.factorit.EcommerceShop.model.Client;
import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.repository.ClientRepository;
import com.factorit.EcommerceShop.repository.ProductRepository;
import com.factorit.EcommerceShop.repository.ShoppingCartRepository;
import com.factorit.EcommerceShop.utils.CartEnum;
import com.factorit.EcommerceShop.utils.ScriptSqlRunner;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final ProductRepository productRepository;

    private final ClientRepository clientRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<List<ShoppingCart>> createNewCart(ShoppingCart cart) {
        List<ShoppingCart> cartList = new ArrayList<>();
        if (cart.getCartName().equals(String.valueOf(CartEnum.COMUN)) || cart.getCartName().equals(String.valueOf(CartEnum.PROMOCIONABLE))
                || cart.getCartName().equals(String.valueOf(CartEnum.VIP))) {
            cart.setCreatedAt(cart.getCreatedAt());
            cartList.add(cart);
            shoppingCartRepository.save(cart);
            //Deberia agregar un atributo mensaje para dar al json un estado
            ScriptSqlRunner.runScript("src/main/resources/scripts/resetIdScript.sql");
        } else {
            return new ResponseEntity<>(cartList, HttpStatus.NOT_ACCEPTABLE);
        }
        //return new ResponseEntity<>("Carrito de compras  con id: " + cart.getId() + " de tipo: " + cart.getCartName() + " ha sido creado con exito!", HttpStatus.OK);
        return new ResponseEntity<>(cartList, HttpStatus.OK);
    }

    public ResponseEntity<?> findCart(Long id) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        List<ShoppingCart> cartList = new ArrayList<>();
        ShoppingCart cart = shoppingCartRepository.getById(id);
        if (cartAux.isEmpty()) {
            //return new ResponseEntity<>("Carrito buscado es inexistente", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Carrito buscado es inexistente");
        }
        cartList.add(cart);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(cartList);
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
        //List<ShoppingCart> cartList = new ArrayList<>();
        if (cartAux.isEmpty()) {
            //return new ResponseEntity<>(cartList, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("El carrito buscado es inexistente", HttpStatus.NOT_FOUND);
        }
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        shoppingCart.setBuyCount(incrementCart(shoppingCart));
        product.setShoppingCart(shoppingCart);
        product.setQuantity("1");
        shoppingCart.addProduct(product);

        double totalAmount = 0;
        for (int i = 0; i < shoppingCart.getProductsList().size(); i++) {
            double value = shoppingCart.getProductsList().get(i).getPrice().doubleValue();
            totalAmount += value;
            BigDecimal decimal = BigDecimal.valueOf(totalAmount);
            shoppingCart.setTotalAmount(decimal);
        }

        //cartList.add(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        //productRepository.save(product);
        return new ResponseEntity<>("Producto agredado al carrito: " + shoppingCart.getProductsList(), HttpStatus.OK);
        //return new ResponseEntity<>(cartList, HttpStatus.OK);
    }

    public ResponseEntity<?> addProductFromMarket(@RequestBody Product product, Long id) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        //List<ShoppingCart> cartList = new ArrayList<>();
        if (cartAux.isEmpty()) {
            //return new ResponseEntity<>(cartList, HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Carrito buscado es inexistente");
        }
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        shoppingCart.setBuyCount(incrementCart(shoppingCart));
        product = productRepository.getById(product.getId());
        product.setShoppingCart(shoppingCart);
        product.setQuantity(product.getQuantity());
        shoppingCart.addProduct(product);

        double totalAmount = 0;
        for (int i = 0; i < shoppingCart.getProductsList().size(); i++) {
            double value = shoppingCart.getProductsList().get(i).getPrice().doubleValue();
            totalAmount += value;
            BigDecimal decimal = BigDecimal.valueOf(totalAmount);
            shoppingCart.setTotalAmount(decimal);
        }

        //cartList.add(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        //productRepository.save(product);
        System.out.println("Producto agregado con exito");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(shoppingCart.getProductsList());
    }

    public ResponseEntity<?> addProductByName(@RequestBody Product product, Long id) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        Optional<Product> productAux = productRepository.getByName(product.getName());
        //List<ShoppingCart> cartList = new ArrayList<>();
        if (cartAux.isEmpty()) {
            //return new ResponseEntity<>(cartList, HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Carrito buscado es inexistente");
        }
        if (productAux.isEmpty()) {
            //return new ResponseEntity<>(cartList, HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Producto no existe o no esta disponible");
        }
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        shoppingCart.setBuyCount(incrementCart(shoppingCart));
        product = productRepository.findByName(product.getName());

        product.setShoppingCart(shoppingCart);
        product.setQuantity(product.getQuantity());
        shoppingCart.addProduct(product);

        double totalAmount = 0;
        for (int i = 0; i < shoppingCart.getProductsList().size(); i++) {
            double value = shoppingCart.getProductsList().get(i).getPrice().doubleValue();
            totalAmount += value;
            BigDecimal decimal = BigDecimal.valueOf(totalAmount);
            shoppingCart.setTotalAmount(decimal);
        }

        //cartList.add(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        //productRepository.save(product);
        System.out.println("Producto agregado con exito");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(shoppingCart.getProductsList());
    }


    /**
     * agregar atributo a ShoppingCart de precioInicial, para guardar el precio anterior al descuento
     * asi el usuario puede ver el precio anterior y el precio final con el descuento
     *
     * @return
     */
    public ResponseEntity<?> buyShoppingCart(Long id, Client client) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        //List<ShoppingCart> cartList = new ArrayList<>();
        if (cartAux.isEmpty()) {
            return new ResponseEntity<>("El carrito que quiere comprar es inexistente", HttpStatus.NOT_FOUND);
            //return new ResponseEntity<>(cartList, HttpStatus.NOT_FOUND);
        }
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        shoppingCart.setHasBought(true);

//        client.setName(client.getName());
//        client.setId(client.getId());
        client = clientRepository.getById(client.getId());
        shoppingCart.setClientName(client.getName());

        //client = clientRepository.getById(client.getId());
        if (client.getNextMonthBonus().equals(String.valueOf(CartEnum.BONUS_HIGH_BUY_MONTH))) {
            client.setLevel(String.valueOf(CartEnum.VIP));
            client.setNextMonthBonus("NOT_BONUS");
        } else {
            client.setLevel(shoppingCart.getCartName());
        }

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
        clientRepository.save(client);

        if (shoppingCart.getTotalAmount().doubleValue() > 10000) {
            System.out.println("BONUS DE COMPRA 10000");
            client.setNextMonthBonus(String.valueOf(CartEnum.BONUS_HIGH_BUY_MONTH));
            clientRepository.save(client);
        }

        return new ResponseEntity<>("Compra realizada con exito del carrito: " + shoppingCart +
                " precio total de la compra: $" + shoppingCart.getTotalAmount() + " Cliente: " + client.getName() + ", level: " + client.getLevel(), HttpStatus.OK);
        //return new ResponseEntity<>(cartList, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllCarts() {
        List<ShoppingCart> cartList = shoppingCartRepository.findAll();
        //Gson gson = new Gson();
       // String json = gson.toJson(cartList.toArray());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(cartList);
    }

    public double calculateFinalPrice(double initialAmount, int discount, ShoppingCart shoppingCart) {
        double finalPrice = (initialAmount * discount) / 100;
        BigDecimal inicial = BigDecimal.valueOf(initialAmount);
        BigDecimal finalAmount = BigDecimal.valueOf(finalPrice);
        shoppingCart.setInicialPrice(inicial);
        shoppingCart.setTotalAmount(finalAmount);
        return finalPrice;
    }


    public int incrementCart(ShoppingCart shoppingCart) {
        int cart_products = shoppingCart.getBuyCount();
        cart_products++;
        return cart_products;
    }

}
