package com.jaime.finance_springboot_app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jaime.finance_springboot_app.models.Report;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Schema reportSchema = new Schema<Report>()
                .type("object")
                .description("Modelo de reporte financiero")
                .addProperty("id", new Schema<Long>().type("integer").description("ID único del reporte"))
                .addProperty("totalIncomes", new Schema<Double>().type("number").description("Total de ingresos"))
                .addProperty("totalExpenses", new Schema<Double>().type("number").description("Total de gastos"))
                .addProperty("balance", new Schema<Double>().type("number").description("Balance final"));

        return new OpenAPI()
                .info(new Info()
                    .title("Finance Springboot API")
                    .description("API REST para gestión de finanzas personales")
                    .version("1.0")
                    .contact(new Contact()
                        .name("Jaime")
                        .url("https://github.com/Jaime-GC/finance-springboot-app"))
                    .license(new License()
                        .name("MIT")))
                .components(new Components()
                    .addSchemas("Report", reportSchema));
    }
}