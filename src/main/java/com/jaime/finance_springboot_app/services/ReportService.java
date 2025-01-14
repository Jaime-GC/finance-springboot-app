package com.jaime.finance_springboot_app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaime.finance_springboot_app.models.Expense;
import com.jaime.finance_springboot_app.models.Income;
import com.jaime.finance_springboot_app.repositories.ExpenseRepository;
import com.jaime.finance_springboot_app.repositories.IncomeRepository;

@Service
public class ReportService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public Map<String, Double> getGeneralSummary() {
        List<Income> allIncomes = incomeRepository.findAll();
        List<Expense> allExpenses = expenseRepository.findAll();

        double totalIncomes = 0;
        double totalExpenses = 0;
        
        for (Income income : allIncomes) {
            totalIncomes = totalIncomes + income.getAmount();
        }

        for (Expense expense : allExpenses) {
            totalExpenses = totalExpenses + expense.getAmount();
        }

        double balance = totalIncomes - totalExpenses;

        Map<String, Double> report = new HashMap<>();
        report.put("totalIncomes", totalIncomes);
        report.put("totalExpenses", totalExpenses);
        report.put("balance", balance);

        return report;
    }

    public Map<String, Map<String, Double>> getTotalsByCategory() {
        List<Income> allIncomes = incomeRepository.findAll();
        List<Expense> allExpenses = expenseRepository.findAll();

        Map<String, Double> incomesByCategory = new HashMap<>();
        Map<String, Double> expensesByCategory = new HashMap<>();
        
        // Agrupar ingresos por categoría
        for (Income income : allIncomes) {
            String categoryName = income.getCategory().getName();
            double currentAmount = incomesByCategory.getOrDefault(categoryName, 0.0);
            incomesByCategory.put(categoryName, currentAmount + income.getAmount());
        }

        // Agrupar gastos por categoría
        for (Expense expense : allExpenses) {
            String categoryName = expense.getCategory().getName();
            double currentAmount = expensesByCategory.getOrDefault(categoryName, 0.0);
            expensesByCategory.put(categoryName, currentAmount + expense.getAmount());
        }

        Map<String, Map<String, Double>> result = new HashMap<>();
        result.put("incomes", incomesByCategory);
        result.put("expenses", expensesByCategory);

        return result;
    }



    public Map<String, Map<String, Double>> getTotalsByMonth() {
        List<Income> allIncomes = incomeRepository.findAll();
        List<Expense> allExpenses = expenseRepository.findAll();

        Map<String, Double> incomesByMonth = new HashMap<>();
        Map<String, Double> expensesByMonth = new HashMap<>();

        // Agrupar ingresos por mes
        for (Income income : allIncomes) {
            String month = income.getDate().getMonth().toString().substring(0, 1).toUpperCase() + income.getDate().getMonth().toString().substring(1).toLowerCase();
            double currentAmount = incomesByMonth.getOrDefault(month, 0.0);
            incomesByMonth.put(month, currentAmount + income.getAmount());
        }

        // Agrupar gastos por mes
        for (Expense expense : allExpenses) {
            String month = expense.getDate().getMonth().toString().substring(0, 1).toUpperCase() + expense.getDate().getMonth().toString().substring(1).toLowerCase();
            double currentAmount = expensesByMonth.getOrDefault(month, 0.0);
            expensesByMonth.put(month, currentAmount + expense.getAmount());
        }

        Map<String, Map<String, Double>> result = new HashMap<>();
        result.put("incomes", incomesByMonth);
        result.put("expenses", expensesByMonth);

        return result;
    }
}