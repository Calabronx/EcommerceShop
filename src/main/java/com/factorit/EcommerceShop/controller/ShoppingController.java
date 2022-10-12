package com.factorit.EcommerceShop.controller;

import com.factorit.EcommerceShop.model.Client;
import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
// realiza el crossing de origenes de la aplicacion spring con la app de Angular 9
@RestController
@RequestMapping("/api/v1")
/**
 * ShoppingController
 * Controlador de servicios REST para el manejo de requests al Servidor Web
 * Se utiliza el patron de diseño MVC para el manejo  de cliente -> servidor
 */
public class ShoppingController {

    @Autowired
    private ShoppingCartService cartService;

    /**
     * createCart(shoppingCart) crea un nuevo objeto carrito, pasandole por parametro(body en la post request) sea
     * de tipo  COMUN - PROMOCIONABLE POR FECHA - VIP
     * createCart(shoppingCart)
     * Request: POST
     */
    @PostMapping("/create")
    public ResponseEntity<?> createCart(@RequestBody ShoppingCart cart) {
        return cartService.createNewCart(cart);
    }

    /**
     * findCart(id) consulta el estado de un carrito ya creado con el id por parametro
     *
     * @param id del carrito a consultar
     * @return Devuelve la informacion del carrito con un GET Request
     * Request: GET
     */
    @GetMapping("/cartstatus/{id}")
    public ResponseEntity<?> getCartState(@PathVariable Long id) {
        return cartService.findCart(id);
    }

    /**
     * deleteCart(id) elimina un carrito existente
     *
     * @param id del carrito a consultar
     * @return Devuelve un HTTP.OK si lo elimino con exito
     * Request: DELETE
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        return cartService.deleteCart(id);
    }

    /**
     * metodo: add(producto,id_carrito)
     * Funcion: Agrega un producto existente por el id solicitado en el body y el id del carrito al que se le va a agregar, si no existe
     * el producto, o no existe el carrito lanza error HTTP_NOT_FOUND
     *
     * @param product id del producto,id_cart del carrito
     * @return Devuelve un HTTP.OK si lo agrego con exito
     */
    @PostMapping("/addproduct/{id}")
    public ResponseEntity<?> add(@RequestBody Product product, @PathVariable Long id_cart) {
        return cartService.addProductFromMarket(product, id_cart);
    }

    /**
     * buyCart() realiza una post request para comprar un carrito, con el nombre de cliente como parametro en body
     * y el id del carrito que se va a comprar el cliente dado
     */
    @PostMapping("/buy/{id}")
    public ResponseEntity<?> buyCart(@RequestBody Client client, @PathVariable Long id) {
        return cartService.buyShoppingCart(id, client);
    }

    /**
     * getAllShoppingCarts() devuelve una get request de todos los carritos
     */
    @GetMapping("/getallcarts")
    public ResponseEntity<?> getAllShoppingCarts() {
        return cartService.getAllCarts();
    }

    /**
     * deleteAllWithScript() elimina todos los carritos y sus productos con un script sql
     */
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllWithScript() {
        return cartService.deleteAllCarts();
    }

    /**
     * addProductByName() realiza una post request para agregar un producto por su nombre por body, al carrito con el id solicitado
     */
    @PostMapping("/add-name/{id}")
    public ResponseEntity<?> addProductByName(@RequestBody Product product, @PathVariable Long id) {
        return cartService.addProductByName(product, id);
    }
}
