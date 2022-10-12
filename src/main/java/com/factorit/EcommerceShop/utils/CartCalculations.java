package com.factorit.EcommerceShop.utils;

import com.factorit.EcommerceShop.model.Client;
import com.factorit.EcommerceShop.model.ShoppingCart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

/**
 * esta clase cumple la funcionalidad de calcular todos los montos de los carritos
 * Los descuentos de los carritos, bonos,precios finales y iniciales.
 * Calcula el incremento de la cantidad de los carritos
 * Calcula el decremento de la cantidad de productos de los carritos
 */
public class CartCalculations {
    public static final int GENERAL_DESCOUNT = 25;
    public static final int ABOVE_TEN_PRODUCTS_DESCOUNT = 100;
    public static final int ABOVE_TEN_PRODUCTS_DESCOUNT_AND_PROMOCIONABLE = 300;
    public static final int ABOVE_TEN_PRODUCTS_DESCOUNT_AND_VIP = 500;

    public static void changeClientMembership(ShoppingCart shoppingCart, Client client) {
        if (shoppingCart.getCartName().equals(String.valueOf(CartEnum.VIP)) &&
                !client.getLevel().equals(String.valueOf(CartEnum.VIP))) {
            System.out.println("Cliente se ha vuelto membresia VIP!");
            client.setLevel(shoppingCart.getCartName());
            client.setVipClient(true);
            client.setVipAdquireDate(LocalDateTime.now());
            //client.setEndMembershipDate(null);
        } else if (!shoppingCart.getCartName().equals(String.valueOf(CartEnum.VIP)) &&
                client.getLevel().equals(String.valueOf(CartEnum.VIP))) {
            System.out.println("Cliente ha dejado de ser VIP");
            client.setLevel(shoppingCart.getCartName());
            client.setVipClient(false);
            client.setEndMembershipDate(LocalDateTime.now());
        }
    }

    public static void makeDescount(ShoppingCart shoppingCart, Client client) {
        if (shoppingCart.getBuyCount() == 4) {
            double totalAmount = shoppingCart.getTotalAmount().doubleValue();
            double finalPrice = calculateFinalPrice(totalAmount, CartCalculations.GENERAL_DESCOUNT, shoppingCart);
            client.setClient_buys(BigDecimal.valueOf(finalPrice));
            shoppingCart.setDescount(GENERAL_DESCOUNT);
            shoppingCart.setTotalAmount(BigDecimal.valueOf(finalPrice));
        } else if (shoppingCart.getBuyCount() > 10 && shoppingCart.getCartName().equals(String.valueOf(CartEnum.COMUN))) {
            double totalAmount = shoppingCart.getTotalAmount().doubleValue();
            double finalPrice = calculateFinalPrice(totalAmount, CartCalculations.ABOVE_TEN_PRODUCTS_DESCOUNT, shoppingCart);
            client.setClient_buys(BigDecimal.valueOf(finalPrice));
            shoppingCart.setDescount(ABOVE_TEN_PRODUCTS_DESCOUNT);
            shoppingCart.setTotalAmount(BigDecimal.valueOf(finalPrice));
        } else if (shoppingCart.getBuyCount() > 10 && shoppingCart.getCartName().equals(String.valueOf(CartEnum.PROMOCIONABLE))) {
            double totalAmount = shoppingCart.getTotalAmount().doubleValue();
            double finalPrice = calculateFinalPrice(totalAmount, CartCalculations.ABOVE_TEN_PRODUCTS_DESCOUNT_AND_PROMOCIONABLE, shoppingCart);
            client.setClient_buys(BigDecimal.valueOf(finalPrice));
            shoppingCart.setTotalAmount(BigDecimal.valueOf(finalPrice));
            BigDecimal[] arraydecimal = new BigDecimal[shoppingCart.getProductsList().size()];
            Double[] array = new Double[arraydecimal.length];
            for (int i = 0; i < shoppingCart.getProductsList().size(); i++) {
                arraydecimal[i] = shoppingCart.getProductsList().get(i).getPrice();
            }
            for (int i = 0; i < shoppingCart.getProductsList().size(); i++) {
                array[i] = shoppingCart.getProductsList().get(i).getPrice().doubleValue();
            }
            Arrays.sort(array, Collections.reverseOrder());
            BigDecimal highestBuy = BigDecimal.valueOf(array[array.length - 1]);
            shoppingCart.setDescount(ABOVE_TEN_PRODUCTS_DESCOUNT_AND_PROMOCIONABLE);
            client.setClient_buys(highestBuy);
            //faltaria que este ultimo caso bonifique el producto mas barato
        } else if (shoppingCart.getBuyCount() > 10 && shoppingCart.getCartName().equals(String.valueOf(CartEnum.VIP))) {
            double totalAmount = shoppingCart.getTotalAmount().doubleValue();
            double calculateAmount = calculateFinalPrice(totalAmount, CartCalculations.ABOVE_TEN_PRODUCTS_DESCOUNT_AND_VIP, shoppingCart);
            double bonusProduct = calculateLowestBuy(shoppingCart);
            double finalPrice = calculateAmount - bonusProduct;
            shoppingCart.setTotalAmount(BigDecimal.valueOf(finalPrice));
            shoppingCart.setDescount(ABOVE_TEN_PRODUCTS_DESCOUNT_AND_VIP);
            client.setClient_buys(BigDecimal.valueOf(finalPrice));
        }
    }

    public static double calculateFinalPrice(double initialAmount, int discount, ShoppingCart shoppingCart) {
        double finalPrice = 0;
        if (discount == CartCalculations.GENERAL_DESCOUNT) {
            finalPrice = (initialAmount * discount) / 100;
            shoppingCart.setInicialPrice(BigDecimal.valueOf(initialAmount));
            shoppingCart.setTotalAmount(BigDecimal.valueOf(finalPrice));
            return finalPrice;
        } else if ((discount == CartCalculations.ABOVE_TEN_PRODUCTS_DESCOUNT)) {
            finalPrice = initialAmount - discount;
            shoppingCart.setInicialPrice(BigDecimal.valueOf(initialAmount));
            shoppingCart.setTotalAmount(BigDecimal.valueOf(finalPrice));
            return finalPrice;
        } else if ((discount == CartCalculations.ABOVE_TEN_PRODUCTS_DESCOUNT_AND_PROMOCIONABLE)) {
            finalPrice = initialAmount - discount;
            shoppingCart.setInicialPrice(BigDecimal.valueOf(initialAmount));
            shoppingCart.setTotalAmount(BigDecimal.valueOf(finalPrice));
            return finalPrice;
        } else if ((discount == CartCalculations.ABOVE_TEN_PRODUCTS_DESCOUNT_AND_VIP)) {
            finalPrice = initialAmount - discount;
            shoppingCart.setInicialPrice(BigDecimal.valueOf(initialAmount));
            shoppingCart.setTotalAmount(BigDecimal.valueOf(finalPrice));
            return finalPrice;
        }
        return finalPrice;
    }


    public static int incrementCart(ShoppingCart shoppingCart) {
        int cart_products = shoppingCart.getBuyCount();
        cart_products++;
        return cart_products;
    }

    public static void calculateHighestBuy(ShoppingCart shoppingCart, Client client) {
        Double[] array = new Double[shoppingCart.getProductsList().size()];
        for (int i = 0; i < shoppingCart.getProductsList().size(); i++) {
            array[i] = shoppingCart.getProductsList().get(i).getPrice().doubleValue();
        }
        Arrays.sort(array, Collections.reverseOrder());
        BigDecimal highestBuy = BigDecimal.valueOf(array[array.length - 1]);
        client.setClient_buys(highestBuy);
    }

    public static double calculateLowestBuy(ShoppingCart shoppingCart) {
        Double[] array = new Double[shoppingCart.getProductsList().size()];
        for (int i = 0; i < shoppingCart.getProductsList().size(); i++) {
            array[i] = shoppingCart.getProductsList().get(i).getPrice().doubleValue();
        }
        Arrays.sort(array);
        BigDecimal lowestBuy = BigDecimal.valueOf(array[0]);
        double lowestBuyBonus = lowestBuy.doubleValue();
        return lowestBuyBonus;
    }

    public static void calculateInicialTotalAmount(ShoppingCart shoppingCart) {
        double totalAmount = 0;
        for (int i = 0; i < shoppingCart.getProductsList().size(); i++) {
            double value = shoppingCart.getProductsList().get(i).getPrice().doubleValue();
            totalAmount += value;
            BigDecimal decimal = BigDecimal.valueOf(totalAmount);
            shoppingCart.setTotalAmount(decimal);// setea el monto total actual del carrito
        }
    }
}
