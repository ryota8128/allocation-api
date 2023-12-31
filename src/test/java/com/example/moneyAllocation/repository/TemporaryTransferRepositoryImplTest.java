package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.domain.dto.TemporaryTransferDto;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.impl.TemporaryTransferRepositoryImpl;
import com.example.moneyAllocation.repository.mybatis.TemporaryTransferMapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TemporaryTransferRepositoryImplTest {
  @Mock private SqlSession sqlSession;

  @Mock private TemporaryTransferMapper mapper;

  @InjectMocks private TemporaryTransferRepositoryImpl repository;

  private AutoCloseable mocks;

  @BeforeEach
  public void before() {
    mocks = MockitoAnnotations.openMocks(this);
    Mockito.doReturn(mapper).when(sqlSession).getMapper(TemporaryTransferMapper.class);
  }

  @AfterEach
  public void after() throws Exception {
    mocks.close();
    Mockito.verify(sqlSession, Mockito.times(1)).getMapper(TemporaryTransferMapper.class);
  }

  @Test
  void find() {
    List<TemporaryTransfer> temporaryTransferList = new ArrayList<>();
    temporaryTransferList.add(TemporaryTransfer.from(TemporaryTransferDto.builder().build()));
    TransferSelector selector = TransferSelector.withId(null, null);
    Mockito.doReturn(temporaryTransferList).when(mapper).find(selector);
    List<TemporaryTransfer> result = repository.find(selector);
    assertEquals(temporaryTransferList, result);
    Mockito.verify(mapper, Mockito.times(1)).find(selector);
  }

  @Test
  void findOne() {
    TemporaryTransfer temporaryTransfer =
        TemporaryTransfer.from(TemporaryTransferDto.builder().build());
    TransferSelector selector = TransferSelector.withId(null, null);

    Mockito.doReturn(temporaryTransfer).when(mapper).findOne(selector);
    TemporaryTransfer result = repository.findOne(selector);
    assertEquals(temporaryTransfer, result);
    Mockito.verify(mapper, Mockito.times(1)).findOne(selector);
  }

  @Test
  void findOneFail() {
    TransferSelector selector = TransferSelector.withId(null, null);

    Mockito.doReturn(null).when(mapper).findOne(selector);
    assertThrows(ResourceNotFoundException.class, () -> repository.findOne(selector));
    Mockito.verify(mapper, Mockito.times(1)).findOne(selector);
  }

  @Test
  void add() {
    TemporaryTransfer temporaryTransfer =
        TemporaryTransfer.from(TemporaryTransferDto.builder().build());
    Mockito.doReturn(1).when(mapper).add(temporaryTransfer);
    repository.add(temporaryTransfer);
    Mockito.verify(mapper, Mockito.times(1)).add(temporaryTransfer);
  }

  @Test
  void addFail() {
    TemporaryTransfer temporaryTransfer =
        TemporaryTransfer.from(TemporaryTransferDto.builder().build());
    Mockito.doReturn(0).when(mapper).add(temporaryTransfer);
    assertThrows(RuntimeException.class, () -> repository.add(temporaryTransfer));
    Mockito.verify(mapper, Mockito.times(1)).add(temporaryTransfer);
  }

  @Test
  void set() {
    TemporaryTransfer temporaryTransfer = TemporaryTransfer.from(TemporaryTransferDto.builder().build());
    Mockito.doReturn(1).when(mapper).set(temporaryTransfer);
    repository.set(temporaryTransfer);
    Mockito.verify(mapper, Mockito.times(1)).set(temporaryTransfer);
  }

  @Test
  void setFail() {
    TemporaryTransfer temporaryTransfer = TemporaryTransfer.from(TemporaryTransferDto.builder().build());
    Mockito.doReturn(0).when(mapper).set(temporaryTransfer);
    assertThrows(ResourceNotFoundException.class, () -> repository.set(temporaryTransfer));
    Mockito.verify(mapper, Mockito.times(1)).set(temporaryTransfer);
  }

  @Test
  void delete() {
    TransferSelector selector = TransferSelector.withId(null, null);

    Mockito.doReturn(1).when(mapper).delete(selector);
    repository.delete(selector);
    Mockito.verify(mapper, Mockito.times(1)).delete(selector);
  }

  @Test
  void deleteByTransferId() {
    TransferSelector selector = TransferSelector.withTransferId(1L, null);

    Mockito.doReturn(0).when(mapper).delete(selector);
    repository.delete(selector);
    Mockito.verify(mapper, Mockito.times(1)).delete(selector);
  }

  @Test
  void deleteFail() {
    TransferSelector selector = TransferSelector.withId(1L, null);

    Mockito.doReturn(0).when(mapper).delete(selector);
    assertThrows(ResourceNotFoundException.class, () -> repository.delete(selector));
    Mockito.verify(mapper, Mockito.times(1)).delete(selector);
  }

  @Test
  void setNullAccount() {
    Long accountId = 1L;
    Mockito.doNothing().when(mapper).setNullAccount(accountId);
    repository.setNullAccount(accountId);
    Mockito.verify(mapper, Mockito.times(1)).setNullAccount(accountId);
  }
}
