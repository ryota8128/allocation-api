package com.example.moneyAllocation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.domain.service.TransferDomainService;
import com.example.moneyAllocation.exception.ResourceValidationException;
import com.example.moneyAllocation.repository.RegularTransferRepository;
import com.example.moneyAllocation.service.impl.RegularTransferServiceImpl;
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
  @Mock RegularTransferRepository regularTransferRepository;

  @Mock TransferDomainService transferDomainService;

  @InjectMocks private RegularTransferServiceImpl service;

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
    Long userId = 3L;
    List<RegularTransfer> regularTransferList = new ArrayList<>();
    regularTransferList.add(new RegularTransfer());
    Mockito.doReturn(regularTransferList).when(regularTransferRepository).find(userId);

    List<RegularTransfer> result = service.find(userId);
    assertEquals(regularTransferList, result);
    Mockito.verify(regularTransferRepository, Mockito.times(1)).find(userId);
  }

  @Test
  void findOne() {
    TransferSelector selector = TransferSelector.withId(1L, 1L);
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
    regularTransfer.setRatio(0.3F);
    regularTransfer.setFromAccount(1L);
    regularTransfer.setToAccount(2L);
    regularTransfer.setUserId(1L);

    Mockito.doNothing().when(transferDomainService).checkValidAccounts(regularTransfer);
    Mockito.doReturn(1L).when(regularTransferRepository).add(regularTransfer);
    service.add(regularTransfer);

    Mockito.verify(transferDomainService, Mockito.times(1)).checkValidAccounts(regularTransfer);
    Mockito.verify(regularTransferRepository, Mockito.times(1)).add(regularTransfer);
  }

  @Test
  void addSuccessWithAmount() {
    RegularTransfer regularTransfer = spy(new RegularTransfer());
    regularTransfer.setPercentage(false);
    regularTransfer.setAmount(100);
    regularTransfer.setFromAccount(1L);
    regularTransfer.setToAccount(2L);
    regularTransfer.setUserId(1L);

    Mockito.doNothing().when(regularTransfer).checkRatioValid();
    Mockito.doNothing().when(transferDomainService).checkValidAccounts(regularTransfer);
    Mockito.doReturn(1L).when(regularTransferRepository).add(regularTransfer);
    Mockito.doReturn(regularTransfer)
        .when(regularTransferRepository)
        .findOne(Mockito.any(TransferSelector.class));
    RegularTransfer result = service.add(regularTransfer);

    Mockito.verify(transferDomainService, Mockito.times(1)).checkValidAccounts(regularTransfer);
    Mockito.verify(regularTransferRepository, Mockito.times(1)).add(regularTransfer);
    assertEquals(regularTransfer, result);
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

    Mockito.doThrow(ResourceValidationException.class)
        .when(transferDomainService)
        .checkValidAccounts(regularTransfer);
    assertThrows(ResourceValidationException.class, () -> service.add(regularTransfer));
  }

  @Test
  void setSuccessWithRatio() {

    RegularTransfer regularTransfer = new RegularTransfer();
    regularTransfer.setPercentage(true);
    regularTransfer.setRatio(0F);
    regularTransfer.setFromAccount(1L);
    regularTransfer.setToAccount(2L);
    regularTransfer.setUserId(1L);
    regularTransfer.setId(5L);

    Mockito.doNothing().when(transferDomainService).checkValidAccounts(regularTransfer);
    Mockito.doNothing().when(regularTransferRepository).set(regularTransfer);
    service.set(regularTransfer);

    Mockito.verify(regularTransferRepository, Mockito.times(1)).set(regularTransfer);
  }

  @Test
  void setSuccessWithAmount() {

    RegularTransfer regularTransfer = new RegularTransfer();
    regularTransfer.setPercentage(false);
    regularTransfer.setAmount(100);
    regularTransfer.setFromAccount(1L);
    regularTransfer.setToAccount(2L);
    regularTransfer.setUserId(1L);
    regularTransfer.setId(5L);

    Mockito.doNothing().when(transferDomainService).checkValidAccounts(regularTransfer);
    Mockito.doNothing().when(regularTransferRepository).set(regularTransfer);
    service.set(regularTransfer);

    Mockito.verify(transferDomainService, Mockito.times(1)).checkValidAccounts(regularTransfer);
    Mockito.verify(regularTransferRepository, Mockito.times(1)).set(regularTransfer);
  }

  @Test
  void setWhenRatioUnder0() {
    RegularTransfer regularTransfer = new RegularTransfer();
    regularTransfer.setPercentage(true);
    regularTransfer.setRatio(-0.1F);
    assertThrows(ResourceValidationException.class, () -> service.set(regularTransfer));
  }

  @Test
  void setWhenRatioOver1() {
    RegularTransfer regularTransfer = new RegularTransfer();
    regularTransfer.setPercentage(true);
    regularTransfer.setRatio(1.1F);

    assertThrows(ResourceValidationException.class, () -> service.set(regularTransfer));
  }

  @Test
  void setWhenIllegalAccount() {
    RegularTransfer regularTransfer = new RegularTransfer();

    Mockito.doThrow(ResourceValidationException.class)
        .when(transferDomainService)
        .checkValidAccounts(regularTransfer);
    assertThrows(ResourceValidationException.class, () -> service.set(regularTransfer));
  }

  @Test
  void delete() {
    TransferSelector selector = TransferSelector.withId(null, null);
    Mockito.doNothing().when(regularTransferRepository).delete(selector);
    service.delete(selector);
    Mockito.verify(regularTransferRepository, Mockito.times(1)).delete(selector);
  }
}
