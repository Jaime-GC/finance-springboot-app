package com.jaime.finance_springboot_app.services;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jaime.finance_springboot_app.models.Category;
import com.jaime.finance_springboot_app.repositories.CategoryRepository;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void getAllCategories_ReturnsList() {
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(new Category()));
        List<Category> categories = categoryService.getAllCategories();
        assertThat(categories).hasSize(1);
    }

    @Test
    void createCategory_ShouldPersist() {
        Category cat = new Category();
        cat.setName("Food");
        when(categoryRepository.save(ArgumentMatchers.any(Category.class))).thenReturn(cat);
        Category created = categoryService.createCategory(cat);
        assertThat(created.getName()).isEqualTo("Food");
    }

    @Test
    void getCategory_ShouldReturnCategory() {
        Category cat = new Category();
        cat.setId(1L);
        cat.setName("Food");
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(cat));
        Category found = categoryService.getCategoryById(1L);
        assertThat(found.getName()).isEqualTo("Food");
    }

    @Test
    void updateCategory_ShouldUpdateAndReturn() {
        Category cat = new Category();
        cat.setId(1L);
        cat.setName("Updated Food");
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(cat));
        when(categoryRepository.save(ArgumentMatchers.any(Category.class))).thenReturn(cat);
        Category updated = categoryService.updateCategory(1L, cat);
        assertThat(updated.getName()).isEqualTo("Updated Food");
    }

    @Test
    void deleteCategory_ShouldDelete() {
        Category cat = new Category();
        cat.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(cat));
        categoryService.deleteCategory(1L);
    }
}