package com.factorit.EcommerceShop.service;

import com.factorit.EcommerceShop.model.ShoppingCart;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "shoppingcart"
})
@XmlRootElement(name = "ShoppingCartsDetails")
public class ShoppingCartsResponse {

    @XmlElement(name = "ShoppingCart", required = true)
    protected ShoppingCart shoppingCart;

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}

