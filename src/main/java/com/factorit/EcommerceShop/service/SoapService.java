package com.factorit.EcommerceShop.service;

import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.repository.ShoppingCartRepository;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class SoapService {

    private static final String NAMESPACE_URI = "http://localhost:8080/api/v1/cartstatus/{id}";

    private final ShoppingCartRepository shoppingCartRepository;

    public SoapService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ShoppingCartsDetails")
    @ResponsePayload
    public ShoppingCartsResponse getCart(@RequestPayload final ShoppingCartsResponse request) {
        //List<ShoppingCart> shoppingCart = new ArrayList<>();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart = shoppingCartRepository.getById(shoppingCart.getId());
        request.setShoppingCart(shoppingCart);
        return request;
    }
}
