package com.jaime.finance_springboot_app.services;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaime.finance_springboot_app.models.Category;
import com.jaime.finance_springboot_app.models.Expense;
import com.jaime.finance_springboot_app.repositories.ExpenseRepository;

@Service
public class ExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() { // sirve para obtener todos los gastos
        List<Expense> expenses = expenseRepository.findAll();
        logger.debug("Fetched expenses: {}", expenses);
        return expenses;
    }

    public List<Expense> getExpensesByUserId(Long userId) { // sirve para obtener todos los gastos de un usuario
        return expenseRepository.findByUserId(userId);
    }

    public Expense getExpenseById(Long id) { // sirve para obtener un gasto por su id
        return expenseRepository.findById(id).orElse(null);
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense updatedExpense) { // (id del gasto a actualizar, gasto actualizado)
        return expenseRepository.findById(id).map(expense -> {
            expense.setDescription(updatedExpense.getDescription());
            expense.setAmount(updatedExpense.getAmount());
            expense.setDate(updatedExpense.getDate());
            expense.setUser(updatedExpense.getUser());
            expense.setCategory(updatedExpense.getCategory());
            return expenseRepository.save(expense);
        }).orElse(null);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByCategory(Category category) {
        return expenseRepository.findByCategory(category);
    }

    public List<Expense> getExpensesByDate(Date date) {
        return expenseRepository.findByDate(date);
    }
}
