package com.jaime.finance_springboot_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaime.finance_springboot_app.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}