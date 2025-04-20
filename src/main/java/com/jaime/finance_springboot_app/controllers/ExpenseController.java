package com.jaime.finance_springboot_app.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Expense> createExpense(@PathVariable Long userId, @PathVariable String categoryName, @RequestBody Expense expense) {
        User userById = userService.getUserById(userId);
        Category categoryByName = categoryService.getCategoryByName(categoryName);
        
        if (userById == null || categoryByName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            expense.setUser(userById);
            expense.setCategory(categoryByName);
            Expense createdExpense = expenseService.createExpense(expense);
            return  ResponseEntity.ok(createdExpense);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        if (expense.getUser() == null || expense.getCategory() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            // Check if the category exists, if not, create it
            Category category = categoryService.getCategoryByName(expense.getCategory().getName());
            if (category == null) {
                category = categoryService.createCategory(expense.getCategory());
            }
            expense.setCategory(category);
            
            Expense createdExpense = expenseService.createExpense(expense);
            return ResponseEntity.ok(createdExpense);
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

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Expense> expenses = expenseService.getExpensesByCategory(category);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/date/{date}")
    public List<Expense> getExpensesByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return expenseService.getExpensesByDate(date);
    }
}
