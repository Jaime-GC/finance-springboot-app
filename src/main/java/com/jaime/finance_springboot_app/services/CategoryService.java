package com.jaime.finance_springboot_app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaime.finance_springboot_app.models.Category;
import com.jaime.finance_springboot_app.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(updatedCategory.getName());
            return categoryRepository.save(category);
        }).orElse(null);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}