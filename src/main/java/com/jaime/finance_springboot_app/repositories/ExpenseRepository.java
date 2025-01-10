package com.jaime.finance_springboot_app.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaime.finance_springboot_app.models.Expense;
import com.jaime.finance_springboot_app.models.Category;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
    List<Expense> findByCategory(Category category);
    List<Expense> findByDate(Date date);
}
