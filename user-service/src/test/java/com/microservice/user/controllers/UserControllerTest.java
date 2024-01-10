package com.microservice.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.user.entities.User;
import com.microservice.user.repository.UserRepository;
import com.microservice.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    static final String BASE_URL = "/users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private static UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void saveUserTest() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setName("test");
        user.setEmail("test@test.com");
        user.setInformation("extra information");

        Mockito.when(userService.saveUser(any())).thenReturn(user);

        mockMvc.perform(post(BASE_URL)
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(user.getUserId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.information").value(user.getInformation()));

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void getUserByIdTest() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setName("test");
        user.setEmail("test@test.com");
        user.setInformation("extra information");

        Mockito.when(userService.getUser(any())).thenReturn(user);

        mockMvc.perform(get(BASE_URL + "/{userId}", user.getUserId())
                                .contentType(objectMapper.writeValueAsString(user))
                                .content(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getUserId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.information").value(user.getInformation()));

        verify(userService, times(1)).getUser(user.getUserId());
    }

    @Test
    void getUsersTest() throws Exception {
        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setName("test");
        user1.setEmail("test@test.com");
        user1.setInformation("extra information");

        User user2 = new User();
        user2.setUserId(UUID.randomUUID().toString());
        user2.setName("test2");
        user2.setEmail("test2@test.com");
        user2.setInformation("non information");

        List<User> userList = Arrays.asList(user1, user2);

        Mockito.when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get(BASE_URL)
                                .contentType(objectMapper.writeValueAsString(userList))
                                .content(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(userList.size())))
                .andExpect(jsonPath("$[0].userId").value(user1.getUserId()))
                .andExpect(jsonPath("$[0].name").value(user1.getName()))
                .andExpect(jsonPath("$[0].email").value(user1.getEmail()))
                .andExpect(jsonPath("$[0].information").value(user1.getInformation()))
                .andExpect(jsonPath("$[1].userId").value(user2.getUserId()))
                .andExpect(jsonPath("$[1].name").value(user2.getName()))
                .andExpect(jsonPath("$[1].email").value(user2.getEmail()))
                .andExpect(jsonPath("$[1].information").value(user2.getInformation()));

        verify(userService, times(1)).getAllUsers();
    }

}
