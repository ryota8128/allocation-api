package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.repository.mybatis.RegularTransferMapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class RegularTransferRepositoryImplTest {
    @Mock
    SqlSession sqlSession;
    @Mock
    RegularTransferMapper mapper;

    private RegularTransferRepository repository;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
        Mockito.doReturn(mapper).when(sqlSession).getMapper(RegularTransferMapper.class);
        repository = new RegularTransferRepositoryImpl(sqlSession);
    }

    @AfterEach
    public void after() {
        Mockito.verify(sqlSession, Mockito.times(1)).getMapper(RegularTransferMapper.class);
    }

    @Test
    void find() {
        List<RegularTransfer> regularTransferList = new ArrayList<>();
        regularTransferList.add(new RegularTransfer());

        RegularTransferSelector selector = new RegularTransferSelector();
        selector.setUserId(1L);
        Mockito.doReturn(regularTransferList).when(mapper).find(selector);
        List<RegularTransfer> result = repository.find(selector);
        assertEquals(regularTransferList, result);
        Mockito.verify(mapper, Mockito.times(1)).find(selector);
    }

    @Test
    void findOne() {
        RegularTransfer regularTransfer = new RegularTransfer();
        RegularTransferSelector selector = new RegularTransferSelector();
        selector.setId(1L);
        selector.setUserId(1L);

        Mockito.doReturn(regularTransfer).when(mapper).findOne(selector);
        RegularTransfer result = repository.findOne(selector);
        assertEquals(regularTransfer, result);
        Mockito.verify(mapper, Mockito.times(1)).findOne(selector);
    }

    @Test
    void add() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doReturn(1).when(mapper).add(regularTransfer);
        repository.add(regularTransfer);
        Mockito.verify(mapper, Mockito.times(1)).add(regularTransfer);
    }

    @Test
    void addFail() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doReturn(0).when(mapper).add(regularTransfer);

        assertThrows(RuntimeException.class, () -> repository.add(regularTransfer));
    }

    @Test
    void set() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doReturn(1).when(mapper).set(regularTransfer);
        repository.set(regularTransfer);
        Mockito.verify(mapper, Mockito.times(1)).set(regularTransfer);
    }

    @Test
    void setFail() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doReturn(0).when(mapper).set(regularTransfer);

        assertThrows(RuntimeException.class, () -> repository.set(regularTransfer));
    }

    @Test
    void delete() {
        RegularTransferSelector selector = new RegularTransferSelector();
        Mockito.doReturn(1).when(mapper).delete(selector);
        repository.delete(selector);
        Mockito.verify(mapper, Mockito.times(1)).delete(selector);
    }

    @Test
    void deleteFail() {
        RegularTransferSelector selector = new RegularTransferSelector();
        Mockito.doReturn(0).when(mapper).delete(selector);
        assertThrows(RuntimeException.class, () -> repository.delete(selector));
    }
}