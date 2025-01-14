package com.jaime.finance_springboot_app.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jaime.finance_springboot_app.models.Category;
import com.jaime.finance_springboot_app.models.Expense;
import com.jaime.finance_springboot_app.models.Income;
import com.jaime.finance_springboot_app.repositories.ExpenseRepository;
import com.jaime.finance_springboot_app.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class ReportServiceTest {

    @Autowired
    private ReportService reportService;
    
    @MockBean
    private IncomeRepository incomeRepository;
    
    @MockBean
    private ExpenseRepository expenseRepository;

    @Test
    void getGeneralSummary_ReturnsMap() {
        Income i = new Income();
        i.setAmount(500);
        Expense e = new Expense();
        e.setAmount(200);

        when(incomeRepository.findAll()).thenReturn(Collections.singletonList(i));
        when(expenseRepository.findAll()).thenReturn(Collections.singletonList(e));

        Map<String, Double> summary = reportService.getGeneralSummary();
        assertThat(summary.get("totalIncomes")).isEqualTo(500.0);
        assertThat(summary.get("totalExpenses")).isEqualTo(200.0);
        assertThat(summary.get("balance")).isEqualTo(300.0);
    }


    
    @Test
    void getTotalByCategory_ReturnsMap() {
        Income i = new Income();
        Category Salary = new Category(1L, "Salary");
        Category Food = new Category(2L, "Food");
        i.setAmount(500);
        i.setCategory(Salary);
        Expense e = new Expense();
        e.setAmount(200);
        e.setCategory(Food);

        when(incomeRepository.findAll()).thenReturn(Collections.singletonList(i));
        when(expenseRepository.findAll()).thenReturn(Collections.singletonList(e));

        Map<String, Map<String, Double>> summary = reportService.getTotalsByCategory();
        assertThat(summary.get("incomes").get("Salary")).isEqualTo(500.0);
        assertThat(summary.get("expenses").get("Food")).isEqualTo(200.0);
    }

    @Test
    void getTotalByMonth_ReturnsMap() {
        Income i = new Income();
        i.setAmount(500);
        i.setDate(LocalDate.of(2023, 1, 1)); 
        Expense e = new Expense();
        e.setAmount(200);
        e.setDate(LocalDate.of(2023, 1, 1));

        when(incomeRepository.findAll()).thenReturn(Collections.singletonList(i));
        when(expenseRepository.findAll()).thenReturn(Collections.singletonList(e));

        Map<String, Map<String, Double>> summary = reportService.getTotalsByMonth();
        assertThat(summary.get("incomes").get("January")).isEqualTo(500.0);
        assertThat(summary.get("expenses").get("January")).isEqualTo(200.0);
    }

}