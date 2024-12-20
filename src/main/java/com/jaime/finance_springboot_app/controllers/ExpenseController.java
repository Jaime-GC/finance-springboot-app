package com.jaime.finance_springboot_app.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaime.finance_springboot_app.models.Category;
import com.jaime.finance_springboot_app.models.Expense;
import com.jaime.finance_springboot_app.models.User;
import com.jaime.finance_springboot_app.services.CategoryService;
import com.jaime.finance_springboot_app.services.ExpenseService;
import com.jaime.finance_springboot_app.services.UserService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/users/{userId}")
    public List<Expense> getExpensesByUserId(@PathVariable Long userId) {
        return expenseService.getExpensesByUserId(userId);
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @PostMapping("/create/{userId}/{categoryName}")
    public Expense createExpense(@PathVariable Long userId, @PathVariable String categoryName, @RequestBody Expense expense) {
        User userById = userService.getUserById(userId);
        Category categoryByName = categoryService.getCategoryByName(categoryName);
        if (userById == null || categoryByName == null) {
            return null;
        } else {
            expense.setUser(userById);
            expense.setCategory(categoryByName);
            return expenseService.createExpense(expense);
        }
    }

    @PutMapping("/update/{id}")
        public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("delete/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/category/{category}")
    public List<Expense> getExpensesByCategory(@PathVariable String category) {
        return expenseService.getExpensesByCategory(category);
    }

    @GetMapping("/date/{date}")
    public List<Expense> getExpensesByDate(@PathVariable Date date) {
        return expenseService.getExpensesByDate(date);
    }
}
