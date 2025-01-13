package com.jaime.finance_springboot_app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaime.finance_springboot_app.services.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Resumen general (ingresos, gastos, balance)
    @GetMapping("/summary")
    public Map<String, Double> getGeneralSummary() {
        return reportService.getGeneralSummary();
    }

    // Total gastado/ingresado por categoría
    @GetMapping("/by-category")
    public Map<String, Map<String, Double>> getTotalsByCategory() {
        return reportService.getTotalsByCategory();
    }

    // Total gastado/ingresado por mes
    @GetMapping("/by-month")
    public Map<String, Map<String, Double>> getTotalsByMonth() {
        return reportService.getTotalsByMonth();
    }
}