package com.jaime.finance_springboot_app;

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

    @MockBean
    private ExpenseService expenseService;

    @MockBean
    private UserService userService;

    @MockBean
    private CategoryService categoryService;

    private List<Expense> expenseList;

    @BeforeEach
    public void setUp() {
        User user = new User(1L, "John Doe", "john@example.com");
        Category category = new Category(1L, "Groceries");
        
        Expense expense1 = new Expense(1L, "Grocery shopping", 100, LocalDate.of(2023, 06, 30), user, category);
        Expense expense2 = new Expense(2L, "Electricity bill", 50, LocalDate.now(), user, category);
        expenseList = Arrays.asList(expense1, expense2);

        Mockito.when(expenseService.getAllExpenses()).thenReturn(expenseList);
        Mockito.when(expenseService.getExpenseById(1L)).thenReturn(expense1);

        //Mock para userService y categoryService
        Mockito.when(userService.getUserById(1L)).thenReturn(user);
        Mockito.when(categoryService.getCategoryByName("Groceries")).thenReturn(category);


        Mockito.when(expenseService.createExpense(Mockito.any(Expense.class))).thenReturn(expense1);
        
        Expense updatedExpense = new Expense(1L, "Updated Grocery shopping", 150, LocalDate.of(2023, 7, 1), user, category);
        Mockito.when(expenseService.updateExpense(Mockito.eq(1L), Mockito.any(Expense.class))).thenReturn(updatedExpense);
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
        String newExpenseJson = "{\"description\": \"Grocery shopping\", \"amount\": \"100\", \"date\": \"2023-06-30\"}";

        mockMvc.perform(post("/expenses/create/1/Groceries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newExpenseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Grocery shopping")))
                .andExpect(jsonPath("$.amount", is(100)))
                .andExpect(jsonPath("$.date", is("2023-06-30")));
    }

    @Test
    public void testUpdateExpense() throws Exception {
        String updatedExpenseJson = "{\"description\": \"Updated Grocery shopping\", \"amount\": \"150\", \"date\": \"2023-07-01\"}";

        mockMvc.perform(put("/expenses/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedExpenseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Updated Grocery shopping")));
    }

    @Test
    public void testDeleteExpense() throws Exception {
        mockMvc.perform(delete("/expenses/delete/1"))
                .andExpect(status().isOk());
    }
}