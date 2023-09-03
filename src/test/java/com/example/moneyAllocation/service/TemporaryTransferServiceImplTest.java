package com.example.moneyAllocation.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.domain.dto.TemporaryTransferDto;
import com.example.moneyAllocation.domain.service.TransferDomainService;
import com.example.moneyAllocation.exception.ResourceValidationException;
import com.example.moneyAllocation.repository.TemporaryTransferRepository;
import com.example.moneyAllocation.service.impl.TemporaryTransferServiceImpl;
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
    private TransferDomainService transferDomainService;

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
        temporaryTransferList.add(TemporaryTransfer.from(TemporaryTransferDto.builder().build()));
        Mockito.doReturn(temporaryTransferList).when(repository).find(selector);
        List<TemporaryTransfer> result = service.find(selector);
        assertEquals(result, temporaryTransferList);
        Mockito.verify(repository, Mockito.times(1)).find(selector);
    }

    @Test
    void findOne() {
        TemporaryTransfer temporaryTransfer = TemporaryTransfer.from(TemporaryTransferDto.builder().build());
        TransferSelector selector = new TransferSelector();
        Mockito.doReturn(temporaryTransfer).when(repository).findOne(selector);
        TemporaryTransfer result = service.findOne(selector);
        assertEquals(temporaryTransfer, result);
        Mockito.verify(repository, Mockito.times(1)).findOne(selector);
    }

    @Test
    void add() {
        TemporaryTransfer temporaryTransfer = TemporaryTransfer.from(TemporaryTransferDto.builder().build());

        Mockito.doNothing().when(transferDomainService).checkValidAccounts(temporaryTransfer);
        Mockito.doNothing().when(repository).add(temporaryTransfer);
        service.add(temporaryTransfer);

        Mockito.verify(transferDomainService, Mockito.times(1)).checkValidAccounts(temporaryTransfer);
        Mockito.verify(repository, Mockito.times(1)).add(temporaryTransfer);
    }


    @Test
    void addWithInvalidFromAccounts() {
        TemporaryTransfer temporaryTransfer = TemporaryTransfer.from(TemporaryTransferDto.builder().build());

        Mockito.doThrow(ResourceValidationException.class).when(transferDomainService).checkValidAccounts(temporaryTransfer);
        assertThrows(ResourceValidationException.class, () -> service.add(temporaryTransfer));
    }

    @Test
    void set() {
        TemporaryTransfer temporaryTransfer = TemporaryTransfer.from(TemporaryTransferDto.builder().build());
        Mockito.doNothing().when(transferDomainService).checkValidAccounts(temporaryTransfer);
        Mockito.doNothing().when(repository).set(temporaryTransfer);
        service.set(temporaryTransfer);
        Mockito.verify(transferDomainService, Mockito.times(1)).checkValidAccounts(temporaryTransfer);
        Mockito.verify(repository, Mockito.times(1)).set(temporaryTransfer);
    }


    @Test
    void setWithInvalidFromAccounts() {
        TemporaryTransfer temporaryTransfer = TemporaryTransfer.from(TemporaryTransferDto.builder().build());

        Mockito.doThrow(ResourceValidationException.class).when(transferDomainService).checkValidAccounts(temporaryTransfer);
        assertThrows(ResourceValidationException.class, () -> service.set(temporaryTransfer));
    }

    @Test
    void delete() {
        TemporaryTransferSelector selector = new TemporaryTransferSelector();

        Mockito.doNothing().when(repository).delete(selector);
        service.delete(selector);
        Mockito.verify(repository, Mockito.times(1)).delete(selector);
    }
}