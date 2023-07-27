package com.example.moneyAllocation.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.exception.ResourceValidationException;
import com.example.moneyAllocation.repository.AccountRepository;
import com.example.moneyAllocation.repository.TemporaryTransferRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TemporaryTransferServiceImplTest {
    @Mock
    private TemporaryTransferRepository repository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TemporaryTransferServiceImpl service;

    private AutoCloseable mocks;

    @BeforeEach
    public void before() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void after() throws Exception {
        mocks.close();
    }

    @Test
    void find() {
        TemporaryTransferSelector selector = new TemporaryTransferSelector();
        List<TemporaryTransfer> temporaryTransferList = new ArrayList<>();
        temporaryTransferList.add(new TemporaryTransfer());
        Mockito.doReturn(temporaryTransferList).when(repository).find(selector);
        List<TemporaryTransfer> result = service.find(selector);
        assertEquals(result, temporaryTransferList);
        Mockito.verify(repository, Mockito.times(1)).find(selector);
    }

    @Test
    void findOne() {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();
        TransferSelector selector = new TransferSelector();
        Mockito.doReturn(temporaryTransfer).when(repository).findOne(selector);
        TemporaryTransfer result = service.findOne(selector);
        assertEquals(temporaryTransfer, result);
        Mockito.verify(repository, Mockito.times(1)).findOne(selector);
    }

    @Test
    void add() {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();
        Mockito.doNothing().when(repository).add(temporaryTransfer);
        service.add(temporaryTransfer);
        Mockito.verify(accountRepository, Mockito.times(0)).findOne(Mockito.any(AccountSelector.class));
        Mockito.verify(repository, Mockito.times(1)).add(temporaryTransfer);
    }

    @Test
    void addWithValidFromAndToAccount() {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();
        temporaryTransfer.setFromAccount(1L);
        temporaryTransfer.setToAccount(2L);
        Mockito.doNothing().when(repository).add(temporaryTransfer);
        Mockito.doReturn(new Account()).when(accountRepository).findOne(Mockito.argThat(arg -> arg.getId() == 1L | arg.getId() == 2L));
        service.add(temporaryTransfer);
        Mockito.verify(accountRepository, Mockito.times(2)).findOne(Mockito.any(AccountSelector.class));
        Mockito.verify(repository, Mockito.times(1)).add(temporaryTransfer);
    }

    @Test
    void addWithInvalidFromAccounts() {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();
        temporaryTransfer.setFromAccount(1L);
        Mockito.doNothing().when(repository).add(temporaryTransfer);
        Mockito.doThrow(ResourceNotFoundException.class).when(accountRepository).findOne(Mockito.argThat(arg -> arg.getId() == 1L));
        assertThrows(ResourceValidationException.class, () -> service.add(temporaryTransfer));
        Mockito.verify(accountRepository, Mockito.times(1)).findOne(Mockito.any(AccountSelector.class));
        Mockito.verify(repository, Mockito.times(0)).add(temporaryTransfer);
    }

    @Test
    void set() {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();
        Mockito.doNothing().when(repository).set(temporaryTransfer);
        service.set(temporaryTransfer);
        Mockito.verify(accountRepository, Mockito.times(0)).findOne(Mockito.any(AccountSelector.class));
        Mockito.verify(repository, Mockito.times(1)).set(temporaryTransfer);
    }

    @Test
    void setWithValidFromAndToAccount() {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();
        temporaryTransfer.setFromAccount(1L);
        temporaryTransfer.setToAccount(2L);
        Mockito.doNothing().when(repository).set(temporaryTransfer);
        Mockito.doReturn(new Account()).when(accountRepository).findOne(Mockito.argThat(arg -> arg.getId() == 1L | arg.getId() == 2L));
        service.set(temporaryTransfer);
        Mockito.verify(accountRepository, Mockito.times(2)).findOne(Mockito.any(AccountSelector.class));
        Mockito.verify(repository, Mockito.times(1)).set(temporaryTransfer);
    }

    @Test
    void setWithInvalidFromAccounts() {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();
        temporaryTransfer.setFromAccount(1L);
        Mockito.doNothing().when(repository).set(temporaryTransfer);
        Mockito.doThrow(ResourceNotFoundException.class).when(accountRepository).findOne(Mockito.argThat(arg -> arg.getId() == 1L));
        assertThrows(ResourceValidationException.class, () -> service.set(temporaryTransfer));
        Mockito.verify(accountRepository, Mockito.times(1)).findOne(Mockito.any(AccountSelector.class));
        Mockito.verify(repository, Mockito.times(0)).set(temporaryTransfer);
    }

    @Test
    void delete() {
        TemporaryTransferSelector selector = new TemporaryTransferSelector();

        Mockito.doNothing().when(repository).delete(selector);
        service.delete(selector);
        Mockito.verify(repository, Mockito.times(1)).delete(selector);
    }
}