package com.jaime.finance_springboot_app.services;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jaime.finance_springboot_app.models.User;
import com.jaime.finance_springboot_app.repositories.UserRepository;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;

    @Test
    void getAllUsers_ReturnsList() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));
        List<User> users = userService.getAllUsers();
        assertThat(users).hasSize(1);
    }

    @Test
    void getUserById_ReturnsUser() {
        User mockUser = new User();
        mockUser.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockUser));
        User found = userService.getUserById(1L);
        assertThat(found.getId()).isEqualTo(1L);
    }

    @Test
    void createUser_ShouldPersist() {
        User user = new User();
        user.setName("Jaime");
        when(userRepository.save(user)).thenReturn(user);
        User saved = userService.createUser(user);
        assertThat(saved.getName()).isEqualTo("Jaime");
    }

    @Test
    void deleteUser_ShouldDelete() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        userService.deleteUser(1L);
    }

    @Test
    void updateUser_ShouldPersist() {
        User user = new User();
        user.setId(1L);
        user.setName("Jaime");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        User saved = userService.updateUser(1L, user);
        assertThat(saved.getName()).isEqualTo("Jaime");
    }
}

