package com.jaime.finance_springboot_app.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.jaime.finance_springboot_app.models.Expense;
import com.jaime.finance_springboot_app.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class ExpenseServiceTest {

    @Autowired
    private ExpenseService expenseService;

    @MockBean
    private ExpenseRepository expenseRepository;

    @Test
    void getAllExpenses_ReturnsList() {
        when(expenseRepository.findAll()).thenReturn(Collections.singletonList(new Expense()));
        List<Expense> expenses = expenseService.getAllExpenses();
        assertThat(expenses).hasSize(1);
    }

    @Test
    void createExpense_ShouldPersist() {
        Expense expense = new Expense();
        expense.setAmount(300);
        when(expenseRepository.save(ArgumentMatchers.any(Expense.class))).thenReturn(expense);
        Expense saved = expenseService.createExpense(expense);
        assertThat(saved.getAmount()).isEqualTo(300);
    }

    @Test
    void getExpenseById_ShouldReturnExpense() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setAmount(500);
        when(expenseRepository.findById(1L)).thenReturn(java.util.Optional.of(expense));
        
        Expense found = expenseService.getExpenseById(1L);
        assertThat(found.getAmount()).isEqualTo(500);
    }

    @Test
    void updateExpense_ShouldModify() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setAmount(100);
        when(expenseRepository.findById(1L)).thenReturn(java.util.Optional.of(expense));
        when(expenseRepository.save(ArgumentMatchers.any(Expense.class))).thenReturn(expense);
        
        Expense updated = expenseService.updateExpense(1L, expense);
        assertThat(updated.getAmount()).isEqualTo(100);
    }

    @Test
    void deleteExpense_ShouldRemove() {
        Expense expense = new Expense();
        expense.setId(1L);
        expenseService.deleteExpense(1L);
        org.mockito.Mockito.verify(expenseRepository).deleteById(1L);
    }
}