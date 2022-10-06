package com.factorit.EcommerceShop;

import com.factorit.EcommerceShop.utils.ScriptSqlRunner;
import com.factorit.EcommerceShop.utils.SystemTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceShopApplication.class, args);
		ScriptSqlRunner.runScript("src/main/resources/scripts/deleteCarts.sql");
		ScriptSqlRunner.runScript("src/main/resources/scripts/alterProductsID.sql");
	}
}
