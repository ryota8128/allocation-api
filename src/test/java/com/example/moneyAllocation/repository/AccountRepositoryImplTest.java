package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.mybatis.AccountMapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class AccountRepositoryImplTest {
  @Mock SqlSession sqlSession;

  @Mock AccountMapper mapper;

  @Mock RegularTransferRepository regularTransferRepository;

  @Mock TemporaryTransferRepository temporaryTransferRepository;

  @InjectMocks @Spy private AccountRepositoryImpl repository;

  @BeforeEach
  public void before() {
    MockitoAnnotations.openMocks(this);
    Mockito.doReturn(mapper).when(sqlSession).getMapper(AccountMapper.class);
  }

  @AfterEach
  public void after() {
    Mockito.verify(sqlSession, Mockito.times(1)).getMapper(AccountMapper.class);
  }

  @Test
  void find() {
    Long ownerId = 2L;
    List<Account> accountList = new ArrayList<>();
    accountList.add(new Account());

    Mockito.doReturn(accountList).when(mapper).find(ownerId);

    List<Account> result = this.repository.find(ownerId);
    assertEquals(accountList, result);
    Mockito.verify(mapper, Mockito.times(1)).find(ownerId);
  }

  @Test
  void findOne() {
    Account account = new Account();
    AccountSelector selector = new AccountSelector();
    selector.setId(1L);
    selector.setOwnerId(1L);

    Mockito.doReturn(account).when(mapper).findOne(selector);
    Account result = repository.findOne(selector);
    assertEquals(account, result);
    Mockito.verify(mapper, Mockito.times(1)).findOne(selector);
  }

  @Test
  void findOneNotExists() {
    AccountSelector selector = new AccountSelector();
    selector.setId(1L);
    selector.setOwnerId(1L);
    Mockito.doReturn(null).when(mapper).findOne(selector);
    assertThrows(ResourceNotFoundException.class, () -> repository.findOne(selector));
    Mockito.verify(mapper, Mockito.times(1)).findOne(selector);
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
    AccountSelector selector = new AccountSelector();
    selector.setId(1L);
    selector.setOwnerId(2L);
    Mockito.doReturn(1).when(mapper).delete(selector);
    Mockito.doNothing()
        .when(repository)
        .setNullToViaThatReferenceDeleteAccount(selector.getOwnerId(), selector.getId());
    Mockito.doNothing().when(regularTransferRepository).setNullAccount(Mockito.eq(1L));
    Mockito.doNothing().when(temporaryTransferRepository).setNullAccount(Mockito.eq(1L));

    this.repository.delete(selector);
    Mockito.verify(mapper, Mockito.times(1)).delete(selector);
    Mockito.verify(regularTransferRepository, Mockito.times(1)).setNullAccount(Mockito.eq(1L));
    Mockito.verify(temporaryTransferRepository, Mockito.times(1)).setNullAccount(Mockito.eq(1L));
    Mockito.verify(repository, Mockito.times(1))
        .setNullToViaThatReferenceDeleteAccount(selector.getOwnerId(), selector.getId());
  }

  @Test
  void deleteFail() {
    AccountSelector selector = new AccountSelector();
    selector.setId(1L);
    selector.setOwnerId(2L);
    Mockito.doReturn(0).when(mapper).delete(selector);
    Mockito.doNothing()
        .when(repository)
        .setNullToViaThatReferenceDeleteAccount(selector.getOwnerId(), selector.getId());
    assertThrowsExactly(ResourceNotFoundException.class, () -> this.repository.delete(selector));
    Mockito.verify(mapper, Mockito.times(1)).delete(selector);
  }
}
