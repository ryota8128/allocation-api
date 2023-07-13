package com.example.moneyAllocation.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.exception.ResourceValidationException;
import com.example.moneyAllocation.repository.AccountRepository;
import com.example.moneyAllocation.repository.RegularTransferRepository;
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

class RegularTransferServiceImplTest {
    @Mock
    RegularTransferRepository regularTransferRepository;

    @InjectMocks
    private RegularTransferServiceImpl service;

    @Mock
    private AccountRepository accountRepository;

    AutoCloseable mocks;

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
        List<RegularTransfer> regularTransferList = new ArrayList<>();
        regularTransferList.add(new RegularTransfer());
        RegularTransferSelector selector = new RegularTransferSelector();
        Mockito.doReturn(regularTransferList).when(regularTransferRepository).find(selector);

        List<RegularTransfer> result = service.find(selector);
        assertEquals(regularTransferList, result);
        Mockito.verify(regularTransferRepository, Mockito.times(1)).find(selector);
    }

    @Test
    void findOne() {
        RegularTransferSelector selector = new RegularTransferSelector();
        selector.setId(1L);
        selector.setUserId(1L);
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doReturn(regularTransfer).when(regularTransferRepository).findOne(selector);

        RegularTransfer result = service.findOne(selector);
        assertEquals(regularTransfer, result);
        Mockito.verify(regularTransferRepository, Mockito.times(1)).findOne(selector);
    }

    @Test
    void addSuccessWithRatio() {

        RegularTransfer regularTransfer = new RegularTransfer();
        regularTransfer.setPercentage(true);
        regularTransfer.setRatio(0F);
        regularTransfer.setFromAccount(1L);
        regularTransfer.setToAccount(2L);
        regularTransfer.setUserId(1L);
        regularTransfer.setId(5L);

        ArgumentMatcher<AccountSelector> matcher = arg -> {
            assertEquals(1L, arg.getOwnerId());
            assertTrue(arg.getId() == 1L || arg.getId() == 2L);
            return true;
        };

        Mockito.doReturn(new Account()).when(accountRepository).findOne(Mockito.argThat(matcher));
        Mockito.doNothing().when(regularTransferRepository).add(regularTransfer);
        service.add(regularTransfer);

        Mockito.verify(accountRepository, Mockito.times(2)).findOne(Mockito.argThat(matcher));
        Mockito.verify(regularTransferRepository, Mockito.times(1)).add(regularTransfer);
    }

    @Test
    void addSuccessWithAmount() {

        RegularTransfer regularTransfer = new RegularTransfer();
        regularTransfer.setPercentage(false);
        regularTransfer.setAmount(100);
        regularTransfer.setFromAccount(1L);
        regularTransfer.setToAccount(2L);
        regularTransfer.setUserId(1L);
        regularTransfer.setId(5L);

        ArgumentMatcher<AccountSelector> matcher = arg -> {
            assertEquals(1L, arg.getOwnerId());
            assertTrue(arg.getId() == 1L || arg.getId() == 2L);
            return true;
        };

        Mockito.doReturn(new Account()).when(accountRepository).findOne(Mockito.argThat(matcher));
        Mockito.doNothing().when(regularTransferRepository).add(regularTransfer);
        service.add(regularTransfer);

        Mockito.verify(accountRepository, Mockito.times(2)).findOne(Mockito.argThat(matcher));
        Mockito.verify(regularTransferRepository, Mockito.times(1)).add(regularTransfer);
    }

    @Test
    void addWhenIllegalRatioNull() {
        RegularTransfer regularTransfer = new RegularTransfer();
        regularTransfer.setPercentage(true);
        regularTransfer.setRatio(null);

        assertThrows(ResourceValidationException.class, () -> service.add(regularTransfer));
    }

    @Test
    void addWhenIllegalIllegalAmountNull() {
        RegularTransfer regularTransfer = new RegularTransfer();
        regularTransfer.setPercentage(false);
        regularTransfer.setAmount(null);

        assertThrows(ResourceValidationException.class, () -> service.add(regularTransfer));
    }

    @Test
    void addWhenRatioUnder0() {
        RegularTransfer regularTransfer = new RegularTransfer();
        regularTransfer.setPercentage(true);
        regularTransfer.setRatio(-0.1F);

        assertThrows(ResourceValidationException.class, () -> service.add(regularTransfer));
    }

    @Test
    void addWhenRatioOver1() {
        RegularTransfer regularTransfer = new RegularTransfer();
        regularTransfer.setPercentage(true);
        regularTransfer.setRatio(1.1F);

        assertThrows(ResourceValidationException.class, () -> service.add(regularTransfer));
    }

    @Test
    void addWhenIllegalAccount() {
        RegularTransfer regularTransfer = new RegularTransfer();
        regularTransfer.setPercentage(true);
        regularTransfer.setRatio(1F);
        regularTransfer.setFromAccount(1L);
        regularTransfer.setToAccount(2L);
        regularTransfer.setUserId(5L);

        ArgumentMatcher<AccountSelector> matcher = arg -> {
            assertEquals(5L, arg.getOwnerId());
            return true;
        };

        Mockito.doThrow(ResourceNotFoundException.class).when(accountRepository).findOne(Mockito.argThat(matcher));
        assertThrows(ResourceValidationException.class, () -> service.add(regularTransfer));
    }


    @Test
    void set() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doNothing().when(regularTransferRepository).set(regularTransfer);
        service.set(regularTransfer);
        Mockito.verify(regularTransferRepository, Mockito.times(1)).set(regularTransfer);
    }

    @Test
    void delete() {
        Long id = 1L;
        Mockito.doNothing().when(regularTransferRepository).delete(id);
        service.delete(id);
        Mockito.verify(regularTransferRepository, Mockito.times(1)).delete(id);
    }

}