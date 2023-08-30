package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.impl.TransferRepositoryImpl;
import com.example.moneyAllocation.repository.mybatis.TransferMapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TransferRepositoryImplTest {
    @Mock
    private SqlSession sqlSession;

    @Mock
    private TransferMapper mapper;

    @Mock TemporaryTransferRepository temporaryTransferRepository;

    @InjectMocks
    private TransferRepositoryImpl repository;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        Mockito.doReturn(mapper).when(sqlSession).getMapper(TransferMapper.class);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
        Mockito.verify(sqlSession, Mockito.times(1)).getMapper(TransferMapper.class);
    }

    @Test
    void find() {
        Long userId = 1L;
        List<Transfer> findResult = new ArrayList<>();
        Mockito.doReturn(findResult).when(mapper).find(userId);
        List<Transfer> result = repository.find(userId);
        assertEquals(findResult, result);
        Mockito.verify(mapper, Mockito.times(1)).find(userId);
    }

    @Test
    void findOne() {
        Transfer findResult = new Transfer();
        TransferSelector selector = new TransferSelector();
        Mockito.doReturn(findResult).when(mapper).findOne(selector);
        Transfer result = repository.findOne(selector);
        assertEquals(findResult, result);
        Mockito.verify(mapper, Mockito.times(1)).findOne(selector);
    }

    @Test
    void failedFindOne() {
        TransferSelector selector = new TransferSelector();
        Mockito.doReturn(null).when(mapper).findOne(selector);
        assertThrows(ResourceNotFoundException.class, () -> repository.findOne(selector));
        Mockito.verify(mapper, Mockito.times(1)).findOne(selector);
    }

    @Test
    void add() {
        Transfer transfer = new Transfer();
        Mockito.doReturn(1).when(mapper).add(transfer);
        repository.add(transfer);
        Mockito.verify(mapper, Mockito.times(1)).add(transfer);
    }

    @Test
    void failedAdd() {
        Transfer transfer = new Transfer();
        Mockito.doReturn(0).when(mapper).add(transfer);
        assertThrows(RuntimeException.class, () -> repository.add(transfer));
        Mockito.verify(mapper, Mockito.times(1)).add(transfer);
    }

    @Test
    void set() {
        Transfer transfer = new Transfer();
        Mockito.doReturn(1).when(mapper).set(transfer);
        repository.set(transfer);
        Mockito.verify(mapper, Mockito.times(1)).set(transfer);
    }

    @Test
    void FailedSet() {
        Transfer transfer = new Transfer();
        Mockito.doReturn(0).when(mapper).set(transfer);
        assertThrows(ResourceNotFoundException.class, () -> repository.set(transfer));
        Mockito.verify(mapper, Mockito.times(1)).set(transfer);
    }

    @Test
    void delete() {
        TransferSelector selector = new TransferSelector();
        selector.setId(5L);
        Mockito.doReturn(1).when(mapper).delete(selector);

        ArgumentMatcher<TemporaryTransferSelector> matcher = argument -> {
            assertEquals(5L, argument.getTransferId());
            return true;
        };
        Mockito.doNothing().when(temporaryTransferRepository).delete(Mockito.argThat(matcher));
        repository.delete(selector);
        Mockito.verify(mapper, Mockito.times(1)).delete(selector);
        Mockito.verify(temporaryTransferRepository, Mockito.times(1)).delete(Mockito.argThat(matcher));
    }

    @Test
    void FailedDelete() {
        TransferSelector selector = new TransferSelector();
        selector.setId(5L);

        ArgumentMatcher<TemporaryTransferSelector> matcher = argument -> {
            assertEquals(5L, argument.getTransferId());
            return true;
        };
        Mockito.doNothing().when(temporaryTransferRepository).delete(Mockito.argThat(matcher));

        Mockito.doReturn(0).when(mapper).delete(selector);
        assertThrows(ResourceNotFoundException.class, () -> repository.delete(selector));
        Mockito.verify(mapper, Mockito.times(1)).delete(selector);
        Mockito.verify(temporaryTransferRepository, Mockito.times(1)).delete(Mockito.argThat(matcher));
    }
}