package com.jaime.finance_springboot_app;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jaime.finance_springboot_app.controllers.ReportController;
import com.jaime.finance_springboot_app.services.ReportService;

@WebMvcTest(ReportController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ReportController.class})
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;


    @BeforeEach
    void setUp() {

        // Prepara los datos de prueba para resumen general
        Map<String, Double> generalSummary = new HashMap<>();
        generalSummary.put("totalIncomes", 1000.0);
        generalSummary.put("totalExpenses", 500.0);
        generalSummary.put("balance", 500.0);

        // Prepara los datos de prueba para totales por categoría
        Map<String, Double> incomesByCategory = new HashMap<>();
        incomesByCategory.put("SALARY", 1000.0);
        Map<String, Double> expensesByCategory = new HashMap<>();
        expensesByCategory.put("FOOD", 500.0);
        
        Map<String, Map<String, Double>> totalsByCategory = new HashMap<>();
        totalsByCategory.put("incomes", incomesByCategory);
        totalsByCategory.put("expenses", expensesByCategory);

        // Prepara los datos de prueba para totales por mes 
        Map<String, Double> incomesByMonth = new HashMap<>();
        incomesByMonth.put("JANUARY", 1000.0);
        Map<String, Double> expensesByMonth = new HashMap<>();
        expensesByMonth.put("JANUARY", 500.0);
        
        Map<String, Map<String, Double>> totalsByMonth = new HashMap<>();
        totalsByMonth.put("incomes", incomesByMonth);
        totalsByMonth.put("expenses", expensesByMonth);

        when(reportService.getGeneralSummary()).thenReturn(generalSummary);
        when(reportService.getTotalsByCategory()).thenReturn(totalsByCategory);
        when(reportService.getTotalsByMonth()).thenReturn(totalsByMonth);
    }

    @Test
    void getGeneralSummary() throws Exception {
        
        mockMvc.perform(get("/reports/summary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalIncomes").value(1000.0))
                .andExpect(jsonPath("$.totalExpenses").value(500.0))
                .andExpect(jsonPath("$.balance").value(500.0));
    }

    @Test
    void getTotalsByCategory() throws Exception {

        mockMvc.perform(get("/reports/by-category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.incomes.SALARY").value(1000.0))
                .andExpect(jsonPath("$.expenses.FOOD").value(500.0));
    }

    @Test
    void getTotalsByMonth() throws Exception {

        mockMvc.perform(get("/reports/by-month")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.incomes.JANUARY").value(1000.0))
                .andExpect(jsonPath("$.expenses.JANUARY").value(500.0));
    }
}
