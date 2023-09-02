package com.example.moneyAllocation.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.repository.AccountRepository;
import com.example.moneyAllocation.repository.RegularTransferRepository;
import com.example.moneyAllocation.repository.TemplateTransferRepository;
import com.example.moneyAllocation.repository.TemporaryTransferRepository;
import com.example.moneyAllocation.service.impl.AccountServiceImpl;
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
  @Mock private AccountRepository accountRepository;
  @Mock private RegularTransferRepository regularTransferRepository;
  @Mock private TemporaryTransferRepository temporaryTransferRepository;
  @Mock private TemplateTransferRepository templateTransferRepository;

  @InjectMocks public AccountServiceImpl target;

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
    Long id = 2L;
    List<Account> accountList = new ArrayList<>();
    accountList.add(new Account());
    Mockito.doReturn(accountList).when(accountRepository).find(id);
    List<Account> result = target.findList(id);
    assertEquals(accountList, result);
    Mockito.verify(accountRepository, Mockito.times(1)).find(id);
  }

  @Test
  void findOne() {
    Account account = new Account();
    AccountSelector selector = new AccountSelector();
    selector.setId(1L);
    selector.setOwnerId(2L);

    Mockito.doReturn(account).when(accountRepository).findOne(selector);
    Account result = target.findOne(selector);
    assertEquals(account, result);
    Mockito.verify(accountRepository, Mockito.times(1)).findOne(selector);
  }

  @Test
  void add() {
    Account account = new Account();
    Mockito.doNothing().when(accountRepository).add(account);
    target.add(account);
    Mockito.verify(accountRepository, Mockito.times(1)).add(account);
  }

  @Test
  void set() {
    Account account = new Account();
    Mockito.doNothing().when(accountRepository).set(account);
    target.set(account);
    Mockito.verify(accountRepository, Mockito.times(1)).set(account);
  }

  @Test
  void delete() {
    AccountSelector selector = new AccountSelector();
    selector.setId(1L);
    Mockito.doNothing().when(accountRepository).delete(selector);
    Mockito.doNothing().when(regularTransferRepository).setNullAccount(selector.getId());
    Mockito.doNothing().when(temporaryTransferRepository).setNullAccount(selector.getId());
    Mockito.doNothing().when(templateTransferRepository).setNullAccount(selector.getId());
    Mockito.doNothing().when(accountRepository).setNullToViaThatReferenceDeleteAccount(selector.getOwnerId(), selector.getId());

    target.delete(selector);
    Mockito.verify(accountRepository, Mockito.times(1)).delete(selector);
    Mockito.verify(regularTransferRepository, Mockito.times(1)).setNullAccount(selector.getId());
    Mockito.verify(templateTransferRepository, Mockito.times(1)).setNullAccount(selector.getId());
    Mockito.verify(accountRepository, Mockito.times(1)).setNullToViaThatReferenceDeleteAccount(selector.getOwnerId(), selector.getId());
  }
}
