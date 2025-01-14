package com.jaime.finance_springboot_app.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.jaime.finance_springboot_app.models.Income;
import com.jaime.finance_springboot_app.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class IncomeServiceTest {

    @Autowired
    private IncomeService incomeService;
    
    @MockBean
    private IncomeRepository incomeRepository;

    @Test
    void getAllIncomes_ReturnsList() {
        when(incomeRepository.findAll()).thenReturn(Collections.singletonList(new Income()));
        List<Income> incomes = incomeService.getAllIncomes();
        assertThat(incomes).hasSize(1);
    }

    @Test
    void createIncome_ShouldPersist() {
        Income income = new Income();
        income.setAmount(1000);
        when(incomeRepository.save(ArgumentMatchers.any(Income.class))).thenReturn(income);
        Income saved = incomeService.createIncome(income);
        assertThat(saved.getAmount()).isEqualTo(1000);
    }

    @Test
    void getIncomeById_ShouldReturnIncome() {
        Income income = new Income();
        income.setId(1L);
        when(incomeRepository.findById(1L)).thenReturn(java.util.Optional.of(income));
        Income found = incomeService.getIncomeById(1L);
        assertThat(found.getId()).isEqualTo(1L);
    }

    @Test
    void updateIncome_ShouldUpdateAndReturn() {
        Income income = new Income();
        income.setId(1L);
        income.setAmount(2000);
        when(incomeRepository.findById(1L)).thenReturn(java.util.Optional.of(income));
        when(incomeRepository.save(ArgumentMatchers.any(Income.class))).thenReturn(income);
        Income updated = incomeService.updateIncome(1L, income);
        assertThat(updated.getAmount()).isEqualTo(2000);
    }

    @Test
    void deleteIncome_ShouldDeleteSuccessfully() {
        Income income = new Income();
        income.setId(1L);
        when(incomeRepository.findById(1L)).thenReturn(java.util.Optional.of(income));
        incomeService.deleteIncome(1L);
    }

}