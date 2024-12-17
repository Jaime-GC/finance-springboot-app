package com.jaime.finance_springboot_app.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaime.finance_springboot_app.models.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUserId(Long userId);
    List<Income> findByCategory(String category);
    List<Income> findByDate(Date date);
}
