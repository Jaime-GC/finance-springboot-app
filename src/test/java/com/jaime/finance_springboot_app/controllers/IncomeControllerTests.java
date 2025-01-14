package com.jaime.finance_springboot_app.controllers;

import java.time.LocalDate;
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
import com.jaime.finance_springboot_app.models.Income;
import com.jaime.finance_springboot_app.models.User;
import com.jaime.finance_springboot_app.services.CategoryService;
import com.jaime.finance_springboot_app.services.IncomeService;
import com.jaime.finance_springboot_app.services.UserService;

@WebMvcTest(IncomeController.class)
public class IncomeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncomeService incomeService;

    @MockBean
    private UserService userService;

    @MockBean
    private CategoryService categoryService;

    private List<Income> incomeList;

    @BeforeEach
    public void setUp() {
        User user = new User(1L, "John Doe", "john@example.com");
        Category category = new Category(1L, "Salary");
        
        Income income1 = new Income(1L, "Salary for June", 5000, LocalDate.of(2023, 06, 30), user, category);
        Income income2 = new Income(2L, "Freelance work", 2000, LocalDate.now(), user, category);
        incomeList = Arrays.asList(income1, income2);

        Mockito.when(incomeService.getAllIncomes()).thenReturn(incomeList);
        Mockito.when(incomeService.getIncomeById(1L)).thenReturn(income1);

        // Mock para userService y categoryService
        Mockito.when(userService.getUserById(1L)).thenReturn(user);
        Mockito.when(categoryService.getCategoryByName("Salary")).thenReturn(category);

        Mockito.when(incomeService.createIncome(Mockito.any(Income.class))).thenReturn(income1);
        
        Income updatedIncome = new Income(1L, "Updated Salary", 6000, LocalDate.of(2023, 7, 1), user, category);
        Mockito.when(incomeService.updateIncome(Mockito.eq(1L), Mockito.any(Income.class))).thenReturn(updatedIncome);
    }

    @Test
    public void testGetAllIncomes() throws Exception {
        mockMvc.perform(get("/incomes/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description", is("Salary for June")))
                .andExpect(jsonPath("$[1].description", is("Freelance work")));
    }

    @Test
    public void testGetIncomeById() throws Exception {
        mockMvc.perform(get("/incomes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Salary for June")));
    }

    @Test
    public void testCreateIncome() throws Exception {
        String newIncomeJson = "{\"description\": \"Salary for June\", \"amount\": \"5000\", \"date\": \"2023-06-30\"}";

        mockMvc.perform(post("/incomes/create/1/Salary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newIncomeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Salary for June")))
                .andExpect(jsonPath("$.amount", is(5000)))
                .andExpect(jsonPath("$.date", is("2023-06-30"))); // Verificar el campo de fecha en el formato correcto
    }

    @Test
    public void testUpdateIncome() throws Exception {
        String updatedIncomeJson = "{\"description\": \"Updated Salary\", \"amount\": \"6000\", \"date\": \"2023-07-01\"}";

        mockMvc.perform(put("/incomes/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedIncomeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Updated Salary")));
    }

    @Test
    public void testDeleteIncome() throws Exception {
        mockMvc.perform(delete("/incomes/delete/1"))
                .andExpect(status().isOk());
    }
}
