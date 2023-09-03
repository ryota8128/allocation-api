package com.example.moneyAllocation.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.security.LoginUser;
import com.example.moneyAllocation.security.LoginUserDetails;
import com.example.moneyAllocation.security.UserRole;
import com.example.moneyAllocation.service.TransferService;
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

class TransferControllerTest {
  @Mock private TransferService transferService;

  @InjectMocks private TransferController transferController;

  private LoginUserDetails loginUserDetails;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
    LoginUser loginUser =
        new LoginUser(1L, "test-user", "test@exampl.xx.xx", "test-encoded-password", false);
    loginUserDetails = new LoginUserDetails(loginUser, UserRole.USER.getGrantedAuthority());
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void find() {
    List<Transfer> findResult = new ArrayList<>();
    Mockito.doReturn(findResult).when(transferService).find(Mockito.eq(1L));

    List<Transfer> result = transferController.find(loginUserDetails);

    assertEquals(findResult, result);
    Mockito.verify(transferService, Mockito.times(1)).find(Mockito.eq(1L));
  }

  @Test
  void findOneWithId() {
    Transfer findResult = new Transfer();
    ArgumentMatcher<TransferSelector> matcher =
        argument -> {
          assertEquals(1L, argument.getUserId());
          assertEquals(2L, argument.getId());
          assertNull(argument.getTitle());
          return true;
        };
    Mockito.doReturn(findResult).when(transferService).findOne(Mockito.argThat(matcher));
    Transfer result = transferController.findOne(loginUserDetails, 2L, null);
    assertEquals(findResult, result);
    Mockito.verify(transferService, Mockito.times(1)).findOne(Mockito.argThat(matcher));
  }

  @Test
  void findOneWithTitle() {
    Transfer findResult = new Transfer();
    ArgumentMatcher<TransferSelector> matcher =
        argument -> {
          assertNull(argument.getId());
          assertEquals("title", argument.getTitle());
          assertEquals(1L, argument.getUserId());
          return true;
        };
    Mockito.doReturn(findResult).when(transferService).findOne(Mockito.argThat(matcher));
    Transfer result = transferController.findOne(loginUserDetails, null, "title");
    assertEquals(findResult, result);
    Mockito.verify(transferService, Mockito.times(1)).findOne(Mockito.argThat(matcher));
  }

  @Test
  void add() {
    Transfer transfer = new Transfer();
    ArgumentMatcher<Transfer> matcher =
        argument -> {
          assertEquals(1L, argument.getUserId());
          return true;
        };
    Mockito.doReturn(new Transfer()).when(transferService).add(Mockito.argThat(matcher));
    transferController.add(loginUserDetails, transfer);
    Mockito.verify(transferService, Mockito.times(1)).add(Mockito.argThat(matcher));
  }

  @Test
  void set() {
    Transfer transfer = new Transfer();
    ArgumentMatcher<Transfer> matcher =
        argument -> {
          assertEquals(1L, argument.getUserId());
          return true;
        };
    Mockito.doNothing().when(transferService).set(Mockito.argThat(matcher));
    transferController.set(loginUserDetails, transfer);
    Mockito.verify(transferService, Mockito.times(1)).set(Mockito.argThat(matcher));
  }

  @Test
  void delete() {
    ArgumentMatcher<TransferSelector> matcher =
        argument -> {
          assertEquals(1L, argument.getUserId());
          assertEquals(2L, argument.getId());
          return true;
        };
    Mockito.doNothing().when(transferService).delete(Mockito.argThat(matcher));
    transferController.delete(loginUserDetails, 2L);
    Mockito.verify(transferService, Mockito.times(1)).delete(Mockito.argThat(matcher));
  }
}
