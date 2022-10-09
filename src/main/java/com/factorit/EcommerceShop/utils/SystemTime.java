package com.factorit.EcommerceShop.utils;

import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.repository.ProductRepository;
import com.factorit.EcommerceShop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class SystemTime {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    private final long MONTH_TIME_SCHEDULED = 2629800000L;

    private final long CHECK_INACTIVE_CARTS_TIME = 300000L;


    public SystemTime() {

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
     */

//    @Scheduled(fixedRate = MONTH_TIME_SCHEDULED)
//    public void testDelete() {
//        //condicionar para que se ejecute recien el tiempo dado, no al inicio de la app
//        System.out.println("running testDelete() method....");
//        ScriptSqlRunner.runScript("src/main/resources/scripts/deleteCarts.sql");
//    }
    //@Scheduled(fixedRate = CHECK_INACTIVE_CARTS_TIME)
    public void deleteInactiveCarts() {
        System.out.println("Running");
        List<ShoppingCart> checkShoppingCarts = shoppingCartRepository.findAll();
        LocalDateTime timeNow = LocalDateTime.now();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        for (int i = 0; i < checkShoppingCarts.size(); i++) {
            ShoppingCart shoppingCart = checkShoppingCarts.get(i);
            //Product product =
            //int cartCreationTime = shoppingCart.getCreatedAt().;
            String dateStart = shoppingCart.getCreatedAt().getHour() + ":" + shoppingCart.getCreatedAt().getMinute() + ":"
                    + shoppingCart.getCreatedAt().getSecond();
            String dateNow = timeNow.getHour() + ":" + timeNow.getMinute() + ":" + timeNow.getSecond();

            long timePassed = 0L;
            try {
                Date date1 = format.parse(dateStart);
                Date date2 = format.parse(dateNow);
                timePassed = date1.getTime() - date2.getTime();
                System.out.println(timePassed);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //int minutes_now = timeNow.getMinute();
            //int timePassed = minutes_now - cartCreationTime;
            if (timePassed > CHECK_INACTIVE_CARTS_TIME && !shoppingCart.isHasBought()) {
                //borrar unicamente el carrito especifico
                System.out.println("Carrito inactivo hace 5  minutos");
                System.out.println(shoppingCart.getProductsList() + " ha sido eliminado por el sistema");
                //borra todos los cart, corregir
                shoppingCart.setDeleteInactive(true);
                shoppingCartRepository.save(shoppingCart);
            } else {
                System.out.println("no pasaron 5 m");
            }
            //deleteParentShoppingCart();
            ScriptSqlRunner.runScript("src/main/resources/scripts/deleteInactiveCarts.sql");
        }
    }

    public void deleteParentShoppingCart(Product product) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db_shoppingapp";
        String username = "root";
        String password = "Biohazard_1";
        Connection conn = DriverManager.getConnection(url, username, password);
        String query = "DELETE FROM products_tbl WHERE products_cart =" + product.getShoppingCart();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.addBatch();
        stmt.executeBatch();
    }


}

