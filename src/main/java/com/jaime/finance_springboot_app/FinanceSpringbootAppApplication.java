package com.jaime.finance_springboot_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceSpringbootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceSpringbootAppApplication.class, args);
		System.out.println("Aplicacion arrancada");
		System.out.println("Se puede acceder a la documentacion de la API en http://localhost:8080/swagger-ui.html");
	}

}
