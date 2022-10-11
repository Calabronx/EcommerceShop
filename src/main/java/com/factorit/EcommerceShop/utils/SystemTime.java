package com.factorit.EcommerceShop.utils;

import com.factorit.EcommerceShop.model.Product;
import com.factorit.EcommerceShop.model.ShoppingCart;
import com.factorit.EcommerceShop.repository.ProductRepository;
import com.factorit.EcommerceShop.repository.ShoppingCartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Esta clase cumple la funcionalidad de scheduler para manejar y chequear el tiempo
 * Tiene funciones para eliminar carritos inactivos de la tabla que han pasado mas de 40 minutos sin comprarse
 */
@Configuration
@EnableScheduling
public class SystemTime {

    private static final Logger logger = LoggerFactory.getLogger(SystemTime.class);

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    private ShoppingCart shoppingCart;

    private final long MONTH_TIME_SCHEDULED = 2629800000L; // Un mes en milisegundos para preguntar cada un mes los carritos inactivos

    private final long CHECK_INACTIVE_CARTS_TIME = 2400000L;// 40 minutos en milisegundos


    public SystemTime() {

    }

    /**
     *
     */

    // ejecuta en el startup de la applicacion y carga los productos a la tabla si esta vacia( pensado para la primera ejecucion
    //de la applicacion.
    @PostConstruct
    public void chargeProductsIfEmpty() {
        List<Product> list = productRepository.findAll();
        if (list.isEmpty()) {
            logger.info("Estado de la base es Vacio");
            logger.info("Cargando 10 productos iniciales en la base");
            ScriptSqlRunner.runScript("src/main/resources/scripts/chargeProducts.sql");
        }
    }

    /**
     * Elimina los carritos inactivos
     */
    @Scheduled(fixedRate = CHECK_INACTIVE_CARTS_TIME)
    public void deleteInactiveCarts() {
        logger.info("Scheduler ejecutando - chequeando carritos inactivos");
        List<ShoppingCart> checkShoppingCarts = shoppingCartRepository.findAll();
        LocalDateTime timeNow = LocalDateTime.now();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        for (int i = 0; i < checkShoppingCarts.size(); i++) {
            shoppingCart = checkShoppingCarts.get(i);
            //Product product =
            //int cartCreationTime = shoppingCart.getCreatedAt().;
            String dateStart = shoppingCart.getCreatedAt().getHour() + ":" + shoppingCart.getCreatedAt().getMinute() + ":"
                    + shoppingCart.getCreatedAt().getSecond();
            String dateNow = timeNow.getHour() + ":" + timeNow.getMinute() + ":" + timeNow.getSecond();

            long timePassed = 0L;
            try {
                Date date1 = format.parse(dateStart);
                Date date2 = format.parse(dateNow);

                timePassed = date2.getTime() - date1.getTime();

                long difference_In_Minutes
                        = (timePassed
                        / (1000 * 60))
                        % 60;

                logger.info("Han pasado " + difference_In_Minutes + " minutos de inactividad del carrito de compras ");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //int minutes_now = timeNow.getMinute();
            //int timePassed = minutes_now - cartCreationTime;
            if (timePassed > CHECK_INACTIVE_CARTS_TIME && !shoppingCart.isHasBought()) {
                //borrar unicamente el carrito especifico
                logger.info("Carrito inactivo hace 40  minutos");
                logger.info(shoppingCart.getProductsList() + " ha sido eliminado por el sistema");

                //borra todos los cart, corregir
                shoppingCart.setDeleteInactive(true);
                shoppingCartRepository.save(shoppingCart);
            } else {
                logger.info("No se encontraron carritos inactivos por mas de 40 minutos");
            }
            //deleteParentShoppingCart();
        }
        //if(productRepository.getById(shoppingCart.get))
        ScriptSqlRunner.runScript("src/main/resources/scripts/deleteInactiveCarts.sql");
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

