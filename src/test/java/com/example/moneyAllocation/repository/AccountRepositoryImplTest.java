package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.repository.mybatis.AccountMapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AccountRepositoryImplTest {
    @Mock
    SqlSession sqlSession;

    @Mock
    AccountMapper mapper;

    private AutoCloseable mocks;

    private AccountRepository repository;

    @BeforeEach
    public void before() {
        mocks = MockitoAnnotations.openMocks(this);
        Mockito.doReturn(mapper).when(sqlSession).getMapper(AccountMapper.class);
        repository = new AccountRepositoryImpl(sqlSession);
    }

    @AfterEach
    public void after() {
        Mockito.verify(sqlSession, Mockito.times(1)).getMapper(AccountMapper.class);
    }

    @Test
    void find() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account());

        AccountSelector selector = new AccountSelector();
        Mockito.doReturn(accountList).when(mapper).find(selector);

        List<Account> result = this.repository.find(selector);
        assertEquals(accountList, result);
        Mockito.verify(mapper, Mockito.times(1)).find(selector);
    }

    @Test
    void findOne() {
        Account account = new Account();
        Long id = 1L;
        Mockito.doReturn(account).when(mapper).findOne(id);
        Account result = repository.findOne(id);
        assertEquals(account, result);
        Mockito.verify(mapper, Mockito.times(1)).findOne(id);
    }

    @Test
    void add() {
        Account account = new Account();
        Mockito.doReturn(1).when(mapper).add(account);
        this.repository.add(account);

        Mockito.verify(mapper, Mockito.times(1)).add(account);
    }

    @Test
    void addFail() {
        Account account = new Account();
        Mockito.doReturn(0).when(mapper).add(account);
        assertThrowsExactly(RuntimeException.class, () -> this.repository.add(account));
        Mockito.verify(mapper, Mockito.times(1)).add(account);
    }


    @Test
    void set() {
        Account account = new Account();
        Mockito.doReturn(1).when(mapper).set(account);
        this.repository.set(account);
        Mockito.verify(mapper, Mockito.times(1)).set(account);
    }

    @Test
    void setFail() {
        Account account = new Account();
        Mockito.doReturn(0).when(mapper).set(account);
        assertThrowsExactly(ResourceNotFoundException.class, () -> this.repository.set(account));
        Mockito.verify(mapper, Mockito.times(1)).set(account);
    }


    @Test
    void delete() {
        Long deleteId = 1L;
        Mockito.doReturn(1).when(mapper).delete(deleteId);
        this.repository.delete(deleteId);
        Mockito.verify(mapper, Mockito.times(1)).delete(deleteId);
    }
    @Test
    void deleteFail() {
        Long deleteId = 1L;
        Mockito.doReturn(0).when(mapper).delete(deleteId);
        assertThrowsExactly(ResourceNotFoundException.class, () -> this.repository.delete(deleteId));
        Mockito.verify(mapper, Mockito.times(1)).delete(deleteId);
    }
}