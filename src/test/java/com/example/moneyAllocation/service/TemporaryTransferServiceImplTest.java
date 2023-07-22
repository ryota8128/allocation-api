package com.example.moneyAllocation.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.domain.TransferSelector;
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
        Long userId = 2L;
        List<TemporaryTransfer> temporaryTransferList = new ArrayList<>();
        temporaryTransferList.add(new TemporaryTransfer());
        Mockito.doReturn(temporaryTransferList).when(repository).find(userId);
        List<TemporaryTransfer> result = service.find(userId);
        assertEquals(result, temporaryTransferList);
        Mockito.verify(repository, Mockito.times(1)).find(userId);
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
        Mockito.verify(repository, Mockito.times(1)).add(temporaryTransfer);
    }

    @Test
    void set() {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();
        Mockito.doNothing().when(repository).set(temporaryTransfer);
        service.set(temporaryTransfer);
        Mockito.verify(repository, Mockito.times(1)).set(temporaryTransfer);
    }

    @Test
    void delete() {
        TemporaryTransferSelector selector = new TemporaryTransferSelector();

        Mockito.doNothing().when(repository).delete(selector);
        service.delete(selector);
        Mockito.verify(repository, Mockito.times(1)).delete(selector);
    }
}