package com.factorit.EcommerceShop.service;

import com.factorit.EcommerceShop.exceptions.ShoppingCartNotFoundException;
import com.factorit.EcommerceShop.model.Client;
import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.repository.ClientRepository;
import com.factorit.EcommerceShop.repository.ProductRepository;
import com.factorit.EcommerceShop.repository.ShoppingCartRepository;
import com.factorit.EcommerceShop.utils.CartCalculations;
import com.factorit.EcommerceShop.utils.CartEnum;
import com.factorit.EcommerceShop.utils.ScriptSqlRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);

    private final ShoppingCartRepository shoppingCartRepository;

    private final ProductRepository productRepository;

    private final ClientRepository clientRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    /**
     * Construye un objeto carrito de compras con el tipo unico solicitado, no puede ser de dos tipos sea VIP y COMUN
     */

    /*
      metodo: createNewCart(Shopping cart)
     * Funcion: Construye un objeto carrito de compras con el tipo unico solicitado, no puede ser de dos tipos sea VIP y COMUN
     * @param  La referencia del objeto shopping cart que recibe por parametro, obtiene el nombre del tipo de carrito por el body.
     * Si el tipo de carrito ingresado no es ninguno de los 3 mencionados, devuelve HTTP.NOT_ACCEPTABLE
     * @return Devuelve un HTTP.OK si lo construyo con exito
     */
    public ResponseEntity<List<ShoppingCart>> createNewCart(ShoppingCart cart) {
        List<ShoppingCart> cartList = new ArrayList<>();
        if (cart.getCartName().equals(String.valueOf(CartEnum.COMUN)) || cart.getCartName().equals(String.valueOf(CartEnum.PROMOCIONABLE))
                || cart.getCartName().equals(String.valueOf(CartEnum.VIP))) { // consulta si el tipo de carrito que se quiere crear es de los 3 tipos unicos, jamas se puede ser de los dos
            cart.setCreatedAt(cart.getCreatedAt());
            cart.setDeleteInactive(true);
            cartList.add(cart);
            shoppingCartRepository.save(cart);
            ScriptSqlRunner.runScript("src/main/resources/scripts/resetIdScript.sql"); // script que altera el id del carrito en la base
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(cartList);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(cartList);
    }

    /**
     * Consulta el estado de un carrito existente,
     * si esta vacio devuelve un HttpStatus.NOT_FOUND
     * De lo contrario lo agrega a una lista y la devuelve con un GET request
     */
    public ResponseEntity<?> findCart(Long id) throws ShoppingCartNotFoundException {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        List<ShoppingCart> cartList = new ArrayList<>();
        ShoppingCart cart = shoppingCartRepository.getById(id);
        if (cartAux.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Carrito buscado es inexistente");
        }
        cartList.add(cart);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(cartList);
    }

    /**
     * Elimina el carrito existente con jpa usando deleteById(id)
     * Si no existe el carrito con el id solicitado, devuelve HTTP.NOT_FOUND
     * Altera el id auto_increment ejecutando un script de la base
     */
    public ResponseEntity<?> deleteCart(Long id) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        if (cartAux.isEmpty()) { // consulta si existe el carrito buscado
            return new ResponseEntity<>("El carrito que ha solicitado eliminar es inexistente", HttpStatus.NOT_FOUND);
        }
        shoppingCartRepository.deleteById(id);//elimina el carrito
        ScriptSqlRunner.runScript("src/main/resources/scripts/resetIdScript.sql");//alter AUTO_INCREMENT = 1 en la base;
        return new ResponseEntity<>("Carrito eliminado con exito", HttpStatus.OK);
    }

    /*
      metodo: addProduct(producto,id_carrito)
     * Funcion: Agrega un producto nuevo en el request body de tipo Producto(Lo crea el usuario a mano) y el id del carrito al que se le va a agregar, si no existe
     * el producto, o no existe el carrito lanza error HTTP_NOT_FOUND
     * Calcula el monto total del carrito(sumando todos los precios de los productos que agrego) y lo guarda en la base.
     * Posdt: Principalmente su objetivo era para probar agregar productos nuevos desde postman
     * @param  producto,id_cart del carrito
     *
     * @return Devuelve un HTTP.OK si lo agrego con exito
     */
    public ResponseEntity<?> addProduct(@RequestBody Product product, Long id) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        if (cartAux.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("El carrito buscado es inexistente");
        }
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        shoppingCart.setBuyCount(CartCalculations.incrementCart(shoppingCart));
        product.setShoppingCart(shoppingCart);
        shoppingCart.addProduct(product);

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

    /*
      metodo: addProductFromMarket(producto,id_carrito)
     * Funcion: Agrega un producto existente por el id solicitado en el body y el id del carrito al que se le va a agregar, si no existe
     * el producto, o no existe el carrito lanza error HTTP_NOT_FOUND
     * Calcula el monto total del carrito(sumando todos los precios de los productos que agrego) y lo guarda en la base.
     * @param  product id del producto,id_cart del carrito
     *
     * @return Devuelve un HTTP.OK si lo agrego con exito
     */
    public ResponseEntity<?> addProductFromMarket(@RequestBody Product product, Long id) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        Optional<Product> productAux = productRepository.findById(product.getId());
        if (cartAux.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Carrito buscado es inexistente");
        }
        if (productAux.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("El producto buscado con el id no existe");
        }
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        if (shoppingCart.isHasBought()) { // si el carrito con el id solicitado, ya fue comprado anteriormente, lanza lo siguiente
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("No se pueden agregar productos a este carrito por que ya ha sido comprado por: " + shoppingCart.getClientName());
        }
        shoppingCart.setBuyCount(CartCalculations.incrementCart(shoppingCart));// incrementa la cantidad de compras del carrito
        product = productRepository.getById(product.getId());// busca el producto agregado
        product.setShoppingCart(shoppingCart);//setea la referencia del shoppingCart con el producto (Many to one)
        product.setQuantity(product.getQuantity());
        shoppingCart.addProduct(product);//agrega el producto a la lista del carrito (One to Many)
        CartCalculations.calculateInicialTotalAmount(shoppingCart); // calcula el monto total del carrito y lo setea en la variable del objeto
        shoppingCartRepository.save(shoppingCart);// guarda el carrito
        logger.info("Producto agregado con exito");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(shoppingCart.getProductsList());
    }

    /*
     metodo: addProductByName(producto,id_carrito)
    * Funcion: Agrega un producto existente por el NOMBRE solicitado en el body y el id del carrito al que se le va a agregar, si no existe
    * el producto, o no existe el carrito lanza error HTTP_NOT_FOUND
    * Calcula el monto total del carrito(sumando todos los precios de los productos que agrego) y lo guarda en la base.
    * @param  product nombre del producto,id_cart del carrito
    *
    * @return Devuelve un HTTP.OK si lo agrego con exito
    */
    public ResponseEntity<?> addProductByName(@RequestBody Product product, Long id) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        Optional<Product> productAux = productRepository.getByName(product.getName());
        if (cartAux.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Carrito buscado es inexistente");
        }
        if (productAux.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Producto no existe o no esta disponible");
        }
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        if (shoppingCart.isHasBought()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("No se pueden agregar productos a este carrito por que ya ha sido comprado por: " + shoppingCart.getClientName());
        }
        shoppingCart.setBuyCount(CartCalculations.incrementCart(shoppingCart));
        String name = product.getName();
        product = productRepository.findByName(name);

        product.setShoppingCart(shoppingCart);
        product.setQuantity(product.getQuantity());
        shoppingCart.addProduct(product);

        CartCalculations.calculateInicialTotalAmount(shoppingCart);// calcula el monto total del carrito y lo setea en la variable del objeto
        shoppingCartRepository.save(shoppingCart);//guarda el carrito
        logger.info("Producto agregado con exito");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(shoppingCart.getProductsList());
    }


    /*
     metodo: buyShoppingCart(id del carrito,cliente)
    * Funcion: funcion para comprar un carrito ya existente, obteniendo el id del carrito a comprar, y el nombre del cliente por body
    * si no existe el carrito lanza error HTTP_NOT_FOUND
    * Calcula el monto total del carrito(sumando todos los precios de los productos que agrego) y lo guarda en la base.
    * Si el carrito viene vacio, no puede comprarlo y devuelve un HTTP.NOT_ACCEPTABLE
    * la funcion setea el level(Membresia) del cliente apenas lo compra.
    * @param  product nombre del producto,id_cart del carrito
    *
    * @return Devuelve un HTTP.OK si lo agrego con exito
    */
    public ResponseEntity<?> buyShoppingCart(Long id, Client client) {
        Optional<ShoppingCart> cartAux = shoppingCartRepository.findById(id);
        if (cartAux.isEmpty()) {
            return new ResponseEntity<>("El carrito que quiere comprar es inexistente", HttpStatus.NOT_FOUND);
        }
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        if (shoppingCart.isHasBought()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("El carrito ya esta comprado por: " + shoppingCart.getClientName());
        }
        if (shoppingCart.getProductsList().size() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("El carrito esta vacio!");
        }
        client = clientRepository.getById(client.getId());
        CartCalculations.changeClientMembership(shoppingCart, client); // obtiene la fecha de cambio de inicio/fin de membresia vip y la guarda en la db
        shoppingCart.setHasBought(true);
        shoppingCart.setDeleteInactive(false);
        client.setLevel(shoppingCart.getCartName());
        shoppingCart.setClientName(client.getName());

        //chequea si el cliente cumplio el bonus de mas de 10 compras anteriormente, si lo es, al comprar pasa a ser VIP
        if (client.getNextMonthBonus().equals(String.valueOf(CartEnum.BONUS_HIGH_BUY_MONTH))) {
            client.setLevel(String.valueOf(CartEnum.VIP));// setea su membresia a VIP
            client.setNextMonthBonus(String.valueOf(CartEnum.NOT_BONUS));
        } else {
            client.setLevel(shoppingCart.getCartName());
        }
        CartCalculations.makeDescount(shoppingCart, client); // calcula el descuento de productos si lo tiene
        shoppingCartRepository.save(shoppingCart);
        clientRepository.save(client);

        if (shoppingCart.getTotalAmount().doubleValue() > 10000) { // si su compra actual supera los $10,000 le da el bonus para ser vip en la proxima compra
            logger.info("BONUS DE COMPRA $10000");
            client.setNextMonthBonus(String.valueOf(CartEnum.BONUS_HIGH_BUY_MONTH));
            clientRepository.save(client);
        }

        return new ResponseEntity<>("Compra realizada con exito del carrito: " + shoppingCart +
                " precio total de la compra: $" + shoppingCart.getTotalAmount() + " Cliente: " + client.getName() + ", level: " + client.getLevel(), HttpStatus.OK);
    }

    /**
     * getAllCarts() obtiene todos los carritos existentes con la funcion findAll() de jpa
     * @return cartList : lista de carritos
     */
    public ResponseEntity<?> getAllCarts() {
        List<ShoppingCart> cartList = shoppingCartRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(cartList);
    }

    /**
     * deleteAllCarts() Elimina todos los carritos y todos los productos, corriendo el script deleteAllScript.sql
     * altera y auto incrementa los id de las tablas en la base para que al agregar otros carritos y productos se
     * refresquen sus ids
     * @return cartList : lista de carritos
     */
    public ResponseEntity<?> deleteAllCarts() {
        logger.info("Ejecutando deleteAllScript.sql");
        logger.info("Eliminando todos los productos y carritos");
        ScriptSqlRunner.runScript("src/main/resources/scripts/deleteAllScript.sql");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Todos los carritos y los productos han sido borrados");
    }
}
