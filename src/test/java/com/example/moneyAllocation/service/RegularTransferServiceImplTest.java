package com.example.moneyAllocation.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.repository.RegularTransferRepository;
import com.example.moneyAllocation.repository.RegularTransferRepositoryImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class RegularTransferServiceImplTest {
    @Mock
    RegularTransferRepository repository;

    private RegularTransferService service;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
        service = new RegularTransferServiceImpl(repository);
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
    void add() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doNothing().when(repository).add(regularTransfer);
        service.add(regularTransfer);
        Mockito.verify(repository, Mockito.times(1)).add(regularTransfer);
    }
}