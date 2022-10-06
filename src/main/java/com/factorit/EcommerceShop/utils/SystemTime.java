package com.factorit.EcommerceShop.utils;

import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemTime {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public SystemTime() {
        System.out.println("Chequeando carritos inactivos");
        //getMinutesPassed();
    }

    /**
     * pensar bien lo que se quiere hacer aca, que primero ejecute un script para borrar los carritos inactivos pasado
     * unos minutos.
     * Luego cuando este funcionando, que calcule hora de inactividad
     * tambien que tenga una funcion para chequear cuanto se compro por mes, asi se puede realizar otro requerimiento del
     * challenge y poder lograr lo sig
     * - Si el cliente en un determinado mes, realizó compras por más de $10.000, pasa a ser
     * considerado VIP en su próxima compra. (considerar el valor de lo que realmente paga el
     * cliente por los carritos luego de aplicarle los descuentos
     * Si el cliente en un determinado mes, no realizó compras, deja de ser VIP si lo era.
     *
     */



//    public void getMinutesPassed() {
//        //List<ShoppingCart> checkShoppingCarts = shoppingCartRepository.findAll();
//        LocalDateTime timeNow = LocalDateTime.now();
//        //for (int i = 0; i < checkShoppingCarts.size(); i++) {
//            //ShoppingCart shoppingCart = checkShoppingCarts.get(i);
//            int cartCreationTime = shoppingCart.getCreatedAt().getMinute();
//            int minutes_now = timeNow.getMinute();
//            int timePassed = cartCreationTime - minutes_now;
//            if (timePassed > 2 && !shoppingCart.isHasBought()) {
//                System.out.println("Carrito inactivo hace " + timePassed + " minutos");
//                System.out.println(shoppingCart.getProductsList() + " ha sido eliminado por el sistema");
//                ScriptSqlRunner.runScript("src/main/resources/scripts/deleteCarts.sql");
//            }
//        }
//    //}
//}
}
