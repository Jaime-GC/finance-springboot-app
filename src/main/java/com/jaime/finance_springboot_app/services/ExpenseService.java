package com.jaime.finance_springboot_app.services;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaime.finance_springboot_app.models.Expense;
import com.jaime.finance_springboot_app.repositories.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() { // sirve para obtener todos los gastos
        return expenseRepository.findAll();
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
            return expenseRepository.save(expense);
        }).orElse(null);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }

    public List<Expense> getExpensesByDate(Date date) {
        return expenseRepository.findByDate(date);
    }
}
