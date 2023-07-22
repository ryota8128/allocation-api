package com.example.moneyAllocation.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.security.LoginUser;
import com.example.moneyAllocation.security.LoginUserDetails;
import com.example.moneyAllocation.security.UserRole;
import com.example.moneyAllocation.service.TemporaryTransferService;
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

class TemporaryTransferControllerTest {

    @Mock
    private TemporaryTransferService service;

    @InjectMocks
    private TemporaryTransferController controller;

    private AutoCloseable mocks;

    private LoginUserDetails loginUserDetails;

    @BeforeEach
    public void before() {
        mocks = MockitoAnnotations.openMocks(this);
        LoginUser loginUser = new LoginUser(1L, "test-user", "test@exampl.xx.xx", "test-encoded-password", false);
        loginUserDetails = new LoginUserDetails(loginUser, UserRole.USER.getGrantedAuthority());
    }

    @AfterEach
    public void after() throws Exception {
        mocks.close();
    }

    @Test
    void find() {
        List<TemporaryTransfer> temporaryTransferList = new ArrayList<>();
        temporaryTransferList.add(new TemporaryTransfer());

        Mockito.doReturn(temporaryTransferList).when(service).find(Mockito.argThat(userId -> userId.equals(1L)));

        List<TemporaryTransfer> result = controller.find(loginUserDetails);

        assertEquals(temporaryTransferList, result);
        Mockito.verify(service, Mockito.times(1)).find(Mockito.argThat(userId -> userId.equals(1L)));
    }

    @Test
    void findOne() {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();

        ArgumentMatcher<TransferSelector> matcher = arg -> {
            assertEquals(1L, arg.getUserId());
            assertEquals(2L, arg.getId());
            return true;
        };
        Mockito.doReturn(temporaryTransfer).when(service).findOne(Mockito.argThat(matcher));
        TemporaryTransfer result = controller.findOne(loginUserDetails, 2L);

        assertEquals(temporaryTransfer, result);
        Mockito.verify(service, Mockito.times(1)).findOne(Mockito.argThat(matcher));
    }

    @Test
    void add() {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();

        ArgumentMatcher<TemporaryTransfer> matcher = argument -> {
            assertEquals(1L, argument.getUserId());
            assertEquals(temporaryTransfer, argument);
            return true;
        };
        Mockito.doNothing().when(service).add(Mockito.argThat(matcher));
        controller.add(loginUserDetails, temporaryTransfer);

        Mockito.verify(service, Mockito.times(1)).add(Mockito.argThat(matcher));
    }

    @Test
    void set() {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();

        ArgumentMatcher<TemporaryTransfer> matcher = argument -> {
            assertEquals(1L, argument.getUserId());
            assertEquals(temporaryTransfer, argument);
            return true;
        };

        Mockito.doNothing().when(service).set(Mockito.argThat(matcher));
        controller.set(loginUserDetails, temporaryTransfer);

        Mockito.verify(service, Mockito.times(1)).set(Mockito.argThat(matcher));
    }

    @Test
    void delete() {
        ArgumentMatcher<TransferSelector> matcher = arg -> {
            assertEquals(1L, arg.getUserId());
            assertEquals(2L, arg.getId());
            return true;
        };
        Mockito.doNothing().when(service).delete(Mockito.argThat(matcher));
        controller.delete(loginUserDetails, 2L);
        Mockito.verify(service, Mockito.times(1)).delete(Mockito.argThat(matcher));
    }
}