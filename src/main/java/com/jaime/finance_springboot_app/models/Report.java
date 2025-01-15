package com.jaime.finance_springboot_app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo para reportes financieros")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "reports")
public class Report {
    @Schema(description = "ID único del reporte", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Total de ingresos", example = "1000.0")
    private double totalIncomes;

    @Schema(description = "Total de gastos", example = "500.0")
    private double totalExpenses;

    @Schema(description = "Balance final", example = "500.0")
    private double balance;
}