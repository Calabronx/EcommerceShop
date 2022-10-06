package com.factorit.EcommerceShop.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ScriptSqlRunner {
    private static Connection conn;

    public static void runScript(String path) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_shoppingapp";
        String username = "root";
        String password = "Biohazard_1";
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            Resource resource = new FileSystemResource(ResourceUtils.getFile(path));
            ScriptUtils.executeSqlScript(conn, resource);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQLException was thrown");
            e.printStackTrace();
        }
    }
}
