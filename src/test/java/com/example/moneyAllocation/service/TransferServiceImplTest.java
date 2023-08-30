package com.example.moneyAllocation.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.repository.TransferRepository;
import com.example.moneyAllocation.service.impl.TransferServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TransferServiceImplTest {
    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferServiceImpl service;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void find() {
        List<Transfer> findResult = new ArrayList<>();
        Long userId = 1L;
        Mockito.doReturn(findResult).when(transferRepository).find(userId);
        List<Transfer> result = service.find(userId);
        assertEquals(findResult, result);
        Mockito.verify(transferRepository, Mockito.times(1)).find(userId);
    }

    @Test
    void findOne() {
        Transfer findResult = new Transfer();
        TransferSelector selector = new TransferSelector();
        Mockito.doReturn(findResult).when(transferRepository).findOne(selector);
        Transfer result = service.findOne(selector);

        assertEquals(findResult, result);
        Mockito.verify(transferRepository, Mockito.times(1)).findOne(selector);
    }

    @Test
    void add() {
        Transfer transfer = new Transfer();
        Mockito.doNothing().when(transferRepository).add(transfer);
        service.add(transfer);
        Mockito.verify(transferRepository, Mockito.times(1)).add(transfer);
    }

    @Test
    void set() {
        Transfer transfer = new Transfer();
        Mockito.doNothing().when(transferRepository).set(transfer);
        service.set(transfer);
        Mockito.verify(transferRepository, Mockito.times(1)).set(transfer);
    }

    @Test
    void delete() {
        TransferSelector selector = new TransferSelector();
        Mockito.doNothing().when(transferRepository).delete(selector);
        service.delete(selector);
        Mockito.verify(transferRepository, Mockito.times(1)).delete(selector);
    }
}