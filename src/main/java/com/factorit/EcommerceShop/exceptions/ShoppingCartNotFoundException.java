package com.factorit.EcommerceShop.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ShoppingCartNotFoundException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartNotFoundException.class);

    private String messageException;

    public ShoppingCartNotFoundException(String message) {
        super(message);
        logger.error("El carrito no ha sido encontrado!");
    }

    public String getMessageException() {
        return messageException;
    }

}
