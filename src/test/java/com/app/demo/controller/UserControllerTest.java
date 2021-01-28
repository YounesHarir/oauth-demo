package com.app.demo.controller;

import com.app.demo.entity.Role;
import com.app.demo.entity.User;
import com.app.demo.exception.UserNotFoundException;
import com.app.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Vector;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Test
    public void getNotFoundTest() throws Exception {
        given(userService.findUserByLogin(anyString()))
                .willThrow(new UserNotFoundException( " USER not found"));

        mockMvc.perform(get("api/user/{username}", 2).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("API_RESOURCE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("username not found"))
                .andExpect(jsonPath("$.ticketId").isNotEmpty()).andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.time").isNotEmpty()).andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors", hasSize(0))).andExpect(jsonPath("$.fieldErrors").exists())
                .andExpect(jsonPath("$.fieldErrors", hasSize(0)));
    }
    @Test
    public void getTest() throws Exception {
        List<Role> roles = null ;
        User user = new User();
        user.setId(3L);
        user.setUsername("younes");
        user.setPassword("younes123");
        user.setRoles(roles);

        given(userService.findUserByLogin(anyString())).willReturn(user);
        mockMvc.perform(get("api/user/{username}", 1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L)).andExpect(jsonPath("$.username").value("younes"))
                .andExpect(jsonPath("$.password").value("younes123"));

    }
    @Test
    public void SaveTest() throws Exception {
        List<Role> roles = null ;
        User user = new User();
        user.setId(3L);
        user.setUsername("younes");
        user.setPassword("younes123");
        user.setRoles(roles);
        doNothing().when(userService).save(org.mockito.ArgumentMatchers.any());
        mockMvc.perform(MockMvcRequestBuilders.post("api/user").content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    public void SaveNullTest() throws Exception {
        doThrow(new UserNotFoundException("Username not found")).when(userService)
                .save(null);
        mockMvc.perform(MockMvcRequestBuilders.post("api/user")).andExpect(status().isBadRequest());
    }
    @Test
    public void getAllNotFoundTest() throws Exception {
        given(userService.findAll())
                .willThrow(new UserNotFoundException( "user not found"));

        mockMvc.perform(get("api/user").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("API_RESOURCE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("user not found"))
                .andExpect(jsonPath("$.ticketId").isNotEmpty()).andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.time").isNotEmpty()).andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors", hasSize(0))).andExpect(jsonPath("$.fieldErrors").exists())
                .andExpect(jsonPath("$.fieldErrors", hasSize(0)));
    }
    @Test
    public void getAllTest() throws Exception {
        List<User> users = new Vector<User>();
        User user = new User();
        List<Role> roles = null ;
        user.setId(3L);
        user.setUsername("younes");
        user.setPassword("younes123");
        user.setRoles(roles);
        users.add(user);
        given(userService.findAll()).willReturn(users);

        mockMvc.perform(get("api/user").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(3L)).andExpect(jsonPath("$.[0].username").value("younes"))
                .andExpect(jsonPath("$.[0].password").value("younes123"));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}