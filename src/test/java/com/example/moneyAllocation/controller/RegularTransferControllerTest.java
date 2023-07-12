package com.example.moneyAllocation.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.repository.util.JsonMaker;
import com.example.moneyAllocation.security.LoginUser;
import com.example.moneyAllocation.security.LoginUserDetails;
import com.example.moneyAllocation.security.UserRole;
import com.example.moneyAllocation.service.RegularTransferService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class RegularTransferControllerTest {
    @Mock
    RegularTransferService service;

    @InjectMocks
    RegularTransferController controller;

    private MockMvc mockMvc;

    private AutoCloseable mocks;

    private LoginUserDetails loginUserDetails;

    @BeforeEach
    public void before() {
        mocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        LoginUser loginUser = new LoginUser(1L, "test-user", "test@exampl.xx.xx", "test-encoded-password", false);
        loginUserDetails = new LoginUserDetails(loginUser, UserRole.USER.getGrantedAuthority());
    }

    @AfterEach
    public void after() throws Exception {
        mocks.close();
    }

    @Test
    void find() {
        List<RegularTransfer> findResult = new ArrayList<>();
        findResult.add(new RegularTransfer());
        Mockito.doReturn(findResult).when(service).find(Mockito.argThat(arg -> arg.getUserId()==1L));

        List<RegularTransfer> result = controller.find(loginUserDetails);
        assertEquals(findResult, result);
        Mockito.verify(service, Mockito.times(1)).find(Mockito.argThat(arg -> arg.getUserId()==1L));
    }

    @Test
    void findOne() {
        RegularTransfer regularTransfer = new RegularTransfer();

        ArgumentMatcher<RegularTransferSelector> matcher = argument -> {
            assertEquals(1L, argument.getId());
            assertEquals(1L, argument.getUserId());
            return true;
        };
        Mockito.doReturn(regularTransfer).when(service).findOne(Mockito.argThat(matcher));

        RegularTransfer result =controller.findOne(loginUserDetails, 1L);
        assertEquals(regularTransfer, result);
        Mockito.verify(service, Mockito.times(1)).findOne(Mockito.argThat(matcher));
    }

    @Test
    void add() throws Exception {

        RegularTransfer regularTransfer = new RegularTransfer();
        regularTransfer.setId(1L);
        regularTransfer.setAmount(30000);

        ArgumentMatcher<RegularTransfer> matcher = argument -> {
            assertEquals(1L, argument.getId());
            assertEquals(30000, argument.getAmount());
            return true;
        };

        mockMvc.perform(MockMvcRequestBuilders.post("/api/regular").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonMaker.toJsonString(regularTransfer)));
        Mockito.verify(service, Mockito.times(1)).add(Mockito.argThat(matcher));
    }

    @Test
    void update() throws Exception {
        RegularTransfer regularTransfer = new RegularTransfer();
        regularTransfer.setFromAccount(3L);
        regularTransfer.setDescription("desc");

        ArgumentMatcher<RegularTransfer> matcher = arg -> {
            assertEquals(3L, arg.getFromAccount());
            assertEquals("desc", arg.getDescription());
            return true;
        };

        Mockito.doNothing().when(service).set(Mockito.argThat(matcher));
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/regular").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonMaker.toJsonString(regularTransfer)));
        Mockito.verify(service, Mockito.times(1)).set(Mockito.argThat(matcher));
    }

    @Test
    void delete() throws Exception {
        ArgumentMatcher<Long> matcher = arg -> arg == 2L;
        Mockito.doNothing().when(service).delete(Mockito.argThat(matcher));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/regular").queryParam("id", "2"));
        Mockito.verify(service, Mockito.times(1)).delete(Mockito.argThat(matcher));
    }

}