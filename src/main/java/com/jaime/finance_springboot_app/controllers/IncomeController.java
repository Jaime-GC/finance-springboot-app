package com.jaime.finance_springboot_app.controllers;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jaime.finance_springboot_app.models.Income;
import com.jaime.finance_springboot_app.models.User;
import com.jaime.finance_springboot_app.services.IncomeService;
import com.jaime.finance_springboot_app.services.UserService;
import com.jaime.finance_springboot_app.models.Category;
import com.jaime.finance_springboot_app.services.CategoryService;

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

    @GetMapping("/category/{category}")
    public List<Income> getIncomesByCategory(@PathVariable String category) {
        return incomeService.getIncomesByCategory(category);
    }

    @GetMapping("/date/{date}")
    public List<Income> getIncomesByDate(@PathVariable Date date) {
        return incomeService.getIncomesByDate(date);
    }

    @PostMapping("/create/{userId}/{categoryName}")
    public Income createIncome(@PathVariable Long userId, @PathVariable String categoryName, @RequestBody Income income) {
        User userById = userService.getUserById(userId);
        Category categoryByName = categoryService.getCategoryByName(categoryName);
        if (userById == null || categoryByName == null) {
            return null;
        } else {
            income.setUser(userById);
            income.setCategory(categoryByName);
            return incomeService.createIncome(income);
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
