package com.jaime.finance_springboot_app.controllers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jaime.finance_springboot_app.models.Category;
import com.jaime.finance_springboot_app.services.CategoryService;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private List<Category> categoryList;



    //"Servidor simulado" actua respondiendo a las llamadas del cliente
    @BeforeEach
    public void setUp() {
        Category category1 = new Category(1L, "Food");
        Category category2 = new Category(2L, "Transport");
        categoryList = Arrays.asList(category1, category2);

        Mockito.when(categoryService.getAllCategories()).thenReturn(categoryList);
        Mockito.when(categoryService.getCategoryById(1L)).thenReturn(category1);
        Mockito.when(categoryService.getCategoryByName("Food")).thenReturn(category1);
        Mockito.when(categoryService.createCategory(Mockito.any(Category.class))).thenReturn(category1);
        Mockito.when(categoryService.updateCategory(Mockito.eq(1L), Mockito.any(Category.class))).thenReturn(category1);
    }

    //"Cliente simulado" actua realizando llamadas al "servidor simulado"
    @Test
    public void testGetAllCategories() throws Exception {
        mockMvc.perform(get("/categories/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Food")))
                .andExpect(jsonPath("$[1].name", is("Transport")));
    }

    @Test
    public void testGetCategoryById() throws Exception {
        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Food")));
    }

    @Test
    public void testGetCategoryByName() throws Exception {
        mockMvc.perform(get("/categories/name/Food"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Food")));
    }

    @Test
    public void testCreateCategory() throws Exception {
        String newCategoryJson = "{\"name\": \"Health\"}";

        mockMvc.perform(post("/categories/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCategoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Food")));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        String updatedCategoryJson = "{\"name\": \"Groceries\"}";

        mockMvc.perform(put("/categories/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCategoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Food")));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        mockMvc.perform(delete("/categories/delete/1"))
                .andExpect(status().isOk());
    }
}
