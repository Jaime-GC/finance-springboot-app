package com.jaime.finance_springboot_app.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaime.finance_springboot_app.models.Category;
import com.jaime.finance_springboot_app.models.Income;
import com.jaime.finance_springboot_app.repositories.IncomeRepository;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public List<Income> getIncomesByUserId(Long userId) {
        return incomeRepository.findByUserId(userId);
    }

    public Income getIncomeById(Long id) {
        return incomeRepository.findById(id).orElse(null);
    }

    public Income createIncome(Income income) { 
        return incomeRepository.save(income);
    }

    public Income updateIncome(Long id, Income updatedIncome) {
        return incomeRepository.findById(id).map(income -> {
            income.setDescription(updatedIncome.getDescription());
            income.setAmount(updatedIncome.getAmount());
            income.setDate(updatedIncome.getDate());
            income.setUser(updatedIncome.getUser());
            income.setCategory(updatedIncome.getCategory());
            return incomeRepository.save(income);
        }).orElse(null);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    public List<Income> getIncomesByCategory(Category category) {
        return incomeRepository.findByCategory(category);
    }

    public List<Income> getIncomesByDate(Date date) {
        return incomeRepository.findByDate(date);
    }
}
