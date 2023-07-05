package com.example.moneyAllocation.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.repository.RegularTransferRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class RegularTransferServiceImplTest {
    @Mock
    RegularTransferRepository repository;

    @InjectMocks
    private RegularTransferServiceImpl service;

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
        Mockito.doReturn(regularTransferList).when(repository).find(selector);

        List<RegularTransfer> result = service.find(selector);
        assertEquals(regularTransferList, result);
        Mockito.verify(repository, Mockito.times(1)).find(selector);
    }

    @Test
    void findOne() {
        Long id = 1L;
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doReturn(regularTransfer).when(repository).findOne(id);

        RegularTransfer result = service.findOne(id);
        assertEquals(regularTransfer, result);
        Mockito.verify(repository, Mockito.times(1)).findOne(id);
    }

    @Test
    void add() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doNothing().when(repository).add(regularTransfer);
        service.add(regularTransfer);
        Mockito.verify(repository, Mockito.times(1)).add(regularTransfer);
    }

    @Test
    void set() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doNothing().when(repository).set(regularTransfer);
        service.set(regularTransfer);
        Mockito.verify(repository, Mockito.times(1)).set(regularTransfer);
    }

    @Test
    void delete() {
        Long id = 1L;
        Mockito.doNothing().when(repository).delete(id);
        service.delete(id);
        Mockito.verify(repository, Mockito.times(1)).delete(id);
    }

}