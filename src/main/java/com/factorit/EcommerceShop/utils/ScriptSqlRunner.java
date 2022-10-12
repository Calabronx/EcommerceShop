package com.factorit.EcommerceShop.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ScriptSqlRunner {
    private static final Logger logger = LoggerFactory.getLogger(SystemTime.class);

    private static Connection conn;
    private static Properties prop = new Properties();


    public static void runScript(String path) {
        String jdbcUrl = "";
        String username = "";
        String password = "";
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            jdbcUrl = prop.getProperty("spring.datasource.url");
            username = prop.getProperty("spring.datasource.username");
            password = prop.getProperty("spring.datasource.password");
        } catch (IOException ex) {
            logger.error("Error al cargar los datos de connexion");
        }
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            Resource resource = new FileSystemResource(ResourceUtils.getFile(path));
            logger.info("Ejecutando script en la ubicacion: " + path);
            ScriptUtils.executeSqlScript(conn, resource);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQLException was thrown");
            logger.error("Error con la ejecucion del script sql");
        }
    }
}
