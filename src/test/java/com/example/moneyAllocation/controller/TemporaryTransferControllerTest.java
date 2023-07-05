package com.example.moneyAllocation.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.repository.util.JsonMaker;
import com.example.moneyAllocation.repository.util.TestDomainDataCreator;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class TemporaryTransferControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TemporaryTransferService service;

    @InjectMocks
    private TemporaryTransferController controller;

    private AutoCloseable mocks;

    @BeforeEach
    public void before() {
        mocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    public void after() throws Exception {
        mocks.close();
    }

    @Test
    void find() throws Exception {
        List<TemporaryTransfer> temporaryTransferList = new ArrayList<>();
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();
        temporaryTransfer.setAmount(2304);
        temporaryTransferList.add(temporaryTransfer);
        ArgumentMatcher<TemporaryTransferSelector> matcher = args -> args.getUserId() == 1L;
        Mockito.doReturn(temporaryTransferList).when(service).find(Mockito.argThat(matcher));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/temporary/list").queryParam("userId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertEquals(JsonMaker.toJsonString(temporaryTransferList), result.getResponse().getContentAsString());
        Mockito.verify(service, Mockito.times(1)).find(Mockito.argThat(matcher));
    }

    @Test
    void add() throws Exception {
        TemporaryTransfer temporaryTransfer = TestDomainDataCreator.temporaryCreate(1L, 2L, 3L, 23000, "desc", 2L);

        ArgumentMatcher<TemporaryTransfer> matcher = argument -> {
            assertEquals(1L, argument.getId());
            assertEquals(2L, argument.getFromAccount());
            assertEquals(3L, argument.getToAccount());
            assertEquals(23000, argument.getAmount());
            assertEquals("desc", argument.getDescription());
            assertEquals(2L, argument.getUserId());
            return true;
        };
        Mockito.doNothing().when(service).add(Mockito.argThat(matcher));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/temporary").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonMaker.toJsonString(temporaryTransfer)));

        Mockito.verify(service, Mockito.times(1)).add(Mockito.argThat(matcher));
    }

    @Test
    void set() throws Exception {
        TemporaryTransfer temporaryTransfer = TestDomainDataCreator.temporaryCreate(1L, 2L, 3L, 23000, "desc", 2L);

        ArgumentMatcher<TemporaryTransfer> matcher = argument -> {
            assertEquals(1L, argument.getId());
            assertEquals(2L, argument.getFromAccount());
            assertEquals(3L, argument.getToAccount());
            assertEquals(23000, argument.getAmount());
            assertEquals("desc", argument.getDescription());
            assertEquals(2L, argument.getUserId());
            return true;
        };
        Mockito.doNothing().when(service).set(Mockito.argThat(matcher));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/temporary").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonMaker.toJsonString(temporaryTransfer)));

        Mockito.verify(service, Mockito.times(1)).set(Mockito.argThat(matcher));
    }

    @Test
    void delete() throws Exception {
        Mockito.doNothing().when(service).delete(Mockito.argThat(arg -> arg == 1L));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/temporary/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).delete(Mockito.argThat(arg -> arg == 1L));
    }
}