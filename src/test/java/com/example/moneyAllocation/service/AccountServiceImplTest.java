package com.example.moneyAllocation.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.repository.AccountRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AccountServiceImplTest {
    @Mock
    public AccountRepository repository;

    @InjectMocks
    public AccountServiceImpl target;

    public AutoCloseable mocks;

    @BeforeEach
    void before() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void after() throws Exception {
        mocks.close();
    }

    @Test
    void findList() {
        AccountSelector selector = new AccountSelector();
        selector.setOwnerId(2L);
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account());
        Mockito.doReturn(accountList).when(repository).find(selector);
        List<Account> result = target.findList(selector);
        assertEquals(accountList, result);
        Mockito.verify(repository, Mockito.times(1)).find(selector);
    }


    @Test
    void findOne() {
        Long id = 1L;
        Account account = new Account();
        Mockito.doReturn(account).when(repository).findOne(id);
        Account result = target.findOne(id);
        assertEquals(account, result);
        Mockito.verify(repository, Mockito.times(1)).findOne(id);
    }
    @Test
    void add() {
        Account account = new Account();
        Mockito.doNothing().when(repository).add(account);
        target.add(account);
        Mockito.verify(repository, Mockito.times(1)).add(account);
    }

    @Test
    void set() {
        Account account = new Account();
        Mockito.doNothing().when(repository).set(account);
        target.set(account);
        Mockito.verify(repository, Mockito.times(1)).set(account);
    }

    @Test
    void delete() {
        Long id = 2L;
        Mockito.doNothing().when(repository).delete(id);
        target.delete(id);
        Mockito.verify(repository, Mockito.times(1)).delete(id);
    }
}