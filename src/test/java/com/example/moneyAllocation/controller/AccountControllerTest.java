package com.example.moneyAllocation.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.repository.util.JsonMaker;
import com.example.moneyAllocation.security.LoginUser;
import com.example.moneyAllocation.security.LoginUserDetails;
import com.example.moneyAllocation.security.UserRole;
import com.example.moneyAllocation.service.AccountService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class AccountControllerTest {
    @Mock
    private AccountService service;

    @InjectMocks
    private AccountController controller;

    private MockMvc mockMvc;

    private AutoCloseable mocks;

    private LoginUserDetails loginUserDetails;

    @BeforeEach
    public void before() {
        mocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        LoginUser loginUser = new LoginUser(1L, "test-user", "test@exampl.xx.xx", "test-encoded-password", false);
        loginUserDetails = new LoginUserDetails(loginUser, UserRole.USER.getGrantedAuthority());
    }

    @AfterEach
    public void after() throws Exception {
        mocks.close();
    }

    @Test
    void find() {

        List<Account> findResult = new ArrayList<>();
        findResult.add(new Account());
        Mockito.doReturn(findResult).when(service).findList(Mockito.argThat(argument -> argument.getOwnerId() == 1L));

        List<Account> result = controller.find(loginUserDetails);

        assertEquals(findResult, result);
        Mockito.verify(service, Mockito.times(1)).findList(Mockito.argThat(argument -> argument.getOwnerId() == 1L));

    }

    @Test
    void findOne() throws Exception {
        Account account = new Account();
        account.setName("ryota");
        Mockito.doReturn(account).when(service).findOne(Mockito.argThat(argument -> argument == 1L));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/account").queryParam("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        assertEquals(JsonMaker.toJsonString(account), result.getResponse().getContentAsString());
        Mockito.verify(service, Mockito.times(1)).findOne(Mockito.argThat(id -> id == 1L));
    }


    @Test
    void add() throws Exception {
        Account account = new Account();
        account.setTransferFee(1000);
        account.setName("test");

        ArgumentMatcher<Account> matcher = argument -> {
            assertEquals(1000, argument.getTransferFee());
            assertEquals("test", argument.getName());
            return true;
        };

        Mockito.doNothing().when(service).add(Mockito.argThat(matcher));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/account").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonMaker.toJsonString(account))).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).add(Mockito.argThat(matcher));
    }

    @Test
    void set() throws Exception {
        Account account = new Account();
        account.setTransferFee(1000);
        account.setName("test");

        ArgumentMatcher<Account> matcher = argument -> {
            assertEquals(1000, argument.getTransferFee());
            assertEquals("test", argument.getName());
            return true;
        };
        Mockito.doNothing().when(service).set(Mockito.argThat(matcher));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/account").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonMaker.toJsonString(account))).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).set(Mockito.argThat(matcher));
    }

    @Test
    void delete() throws Exception {
        Long id = 1L;

        ArgumentMatcher<Long> matcher = argument -> {
            return argument == 1L;
        };
        Mockito.doNothing().when(service).delete(Mockito.argThat(matcher));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/account/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).delete(Mockito.argThat(matcher));

    }
}