package com.jaime.finance_springboot_app;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jaime.finance_springboot_app.controllers.ExpenseController;
import com.jaime.finance_springboot_app.models.Category;
import com.jaime.finance_springboot_app.models.Expense;
import com.jaime.finance_springboot_app.models.User;
import com.jaime.finance_springboot_app.services.CategoryService;
import com.jaime.finance_springboot_app.services.ExpenseService;
import com.jaime.finance_springboot_app.services.UserService;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ExpenseService expenseService;

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ExpenseController expenseController;

    private Expense expense1;
    private Expense expense2;
    private User user;
    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User(1L, "John Doe", "john.doe@example.com");
        category = new Category(1L, "Groceries");

        expense1 = new Expense(1L, "Grocery shopping", 100, LocalDate.of(2023, 6, 30), user, category);
        expense2 = new Expense(2L, "Electricity bill", 50, LocalDate.of(2023, 6, 30), user, category);

        when(expenseService.getAllExpenses()).thenReturn(Arrays.asList(expense1, expense2));
        when(expenseService.getExpenseById(1L)).thenReturn(expense1);
        when(expenseService.createExpense(expense1)).thenReturn(expense1);
        when(expenseService.updateExpense(1L, expense1)).thenReturn(expense1);
        when(userService.getUserById(1L)).thenReturn(user);
        when(categoryService.getCategoryByName("Groceries")).thenReturn(category);
    }

    @Test
    public void testGetAllExpenses() throws Exception {
        mockMvc.perform(get("/expenses/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description", is("Grocery shopping")))
                .andExpect(jsonPath("$[1].description", is("Electricity bill")));
    }

    @Test
    public void testGetExpenseById() throws Exception {
        mockMvc.perform(get("/expenses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Grocery shopping")));
    }

    @Test
    public void testCreateExpense() throws Exception {
        String newExpenseJson = "{\"description\": \"Grocery shopping\", \"amount\": \"100.00\", \"date\": \"2023-06-30\"}";

        mockMvc.perform(post("/expenses/create/1/Groceries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newExpenseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Grocery shopping")));
    }

    @Test
    public void testUpdateExpense() throws Exception {
        String updatedExpenseJson = "{\"description\": \"Updated Grocery shopping\", \"amount\": \"150.00\", \"date\": \"2023-07-01\"}";

        mockMvc.perform(put("/expenses/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedExpenseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Grocery shopping")));
    }

    @Test
    public void testDeleteExpense() throws Exception {
        mockMvc.perform(delete("/expenses/delete/1"))
                .andExpect(status().isOk());
    }
}