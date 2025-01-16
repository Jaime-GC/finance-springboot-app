package com.jaime.finance_springboot_app.controllers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jaime.finance_springboot_app.models.User;
import com.jaime.finance_springboot_app.services.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @BeforeEach
    public void setUp() {
        User user1 = new User(1L, "John Doe", "john@example.com");
        User user2 = new User(2L, "Jane Doe", "jane@example.com");
        User userUpdated = new User(1L, "John Smith", " john@example.com");
        userList = Arrays.asList(user1, user2);

        Mockito.when(userService.getAllUsers()).thenReturn(userList);
        Mockito.when(userService.getUserById(1L)).thenReturn(user1);
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user1);
        Mockito.when(userService.updateUser(Mockito.eq(1L), Mockito.any(User.class))).thenReturn(userUpdated);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[1].name", is("Jane Doe")));
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    public void testCreateUser() throws Exception {
        String newUserJson = "{\"name\": \"John Doe\", \"email\": \"john@example.com\"}";

        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    public void testUpdateUser() throws Exception {
        String updatedUserJson = "{\"name\": \"John Smith\", \"email\": \"johnsmith@example.com\"}";

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Smith")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser_WhenUserNotFound_ShouldReturnNotFoundMessage() throws Exception {
        // Simular que el usuario no existe
        when(userService.getUserById(1L)).thenReturn(null);

        // Ejecutar DELETE request
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User not found"));

        // Verificar que deleteUser nunca fue llamado
        verify(userService, never()).deleteUser(1L);
    }
}
