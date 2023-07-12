package com.example.moneyAllocation.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
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
    void findOne() {
        Account findResult = new Account();
        findResult.setName("ryota");

        ArgumentMatcher<AccountSelector> matcher = argument -> {
            assertEquals(1L, argument.getOwnerId());
            assertEquals(2L, argument.getId());
            return true;
        };
        Mockito.doReturn(findResult).when(service).findOne(Mockito.argThat(matcher));

        Account result = controller.findOne(loginUserDetails, 2L);

        assertEquals(findResult, result);
        Mockito.verify(service, Mockito.times(1)).findOne(Mockito.argThat(matcher));
    }


    @Test
    void add() {
        Account account = new Account();
        account.setName("test");
        account.setNumFreeTransfer(3);
        account.setTransferFee(100);

        Mockito.doNothing().when(service).add(account);
        controller.add(loginUserDetails, account);

        Mockito.verify(service, Mockito.times(1)).add(account);
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