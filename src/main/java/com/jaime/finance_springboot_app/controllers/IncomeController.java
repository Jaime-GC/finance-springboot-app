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
import com.jaime.finance_springboot_app.models.Income;
import com.jaime.finance_springboot_app.models.User;
import com.jaime.finance_springboot_app.services.CategoryService;
import com.jaime.finance_springboot_app.services.IncomeService;
import com.jaime.finance_springboot_app.services.UserService;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<Income> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @GetMapping("/users/{userId}")
    public List<Income> getIncomesByUserId(@PathVariable Long userId) {
        return incomeService.getIncomesByUserId(userId);
    }

    @GetMapping("/{id}")
    public Income getIncomeById(@PathVariable Long id) {
        return incomeService.getIncomeById(id);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Income>> getIncomesByCategory(@PathVariable String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Income> incomes = incomeService.getIncomesByCategory(category);
        return ResponseEntity.ok(incomes);
    }

    @GetMapping("/date/{date}")
    public List<Income> getIncomesByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return incomeService.getIncomesByDate(date);
    }

    @PostMapping("/create/{userId}/{categoryName}")
    public ResponseEntity<Income> createIncome(@PathVariable Long userId, @PathVariable String categoryName, @RequestBody Income income) {
        User userById = userService.getUserById(userId);
        Category categoryByName = categoryService.getCategoryByName(categoryName);

        if (userById == null || categoryByName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            income.setUser(userById);
            income.setCategory(categoryByName);
            Income createdIncome = incomeService.createIncome(income);
            return ResponseEntity.ok(createdIncome);
        }
    }

    @PutMapping("/update/{id}")
    public Income updateIncome(@PathVariable Long id, @RequestBody Income income) {
        return incomeService.updateIncome(id, income);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
    }
}
