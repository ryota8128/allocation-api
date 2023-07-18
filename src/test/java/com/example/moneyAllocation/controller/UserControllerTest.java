package com.example.moneyAllocation.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.repository.util.JsonMaker;
import com.example.moneyAllocation.security.JwtUtils;
import com.example.moneyAllocation.security.LoginUserDetails;
import com.example.moneyAllocation.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserController userController;

    private AutoCloseable mocks;

    private LoginUserDetails loginUserDetails;

    @BeforeEach
    public void before() {
        mocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    void register() throws Exception {
        User user = new User();
        user.setUsername("test-user");
        user.setPassword("test-pass");
        user.setEmail("test@example.xx.xx");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/add").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonMaker.toJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ArgumentMatcher<User> matcher = arg -> {
            assertEquals(false, arg.getAdministratorFlag());
            assertEquals("test-user", arg.getUsername());
            return true;
        };

        Mockito.doNothing().when(userService).add(Mockito.argThat(matcher));
        Mockito.verify(userService, Mockito.times(1)).add(Mockito.argThat(matcher));
    }
}