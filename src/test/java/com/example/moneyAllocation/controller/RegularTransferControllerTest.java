package com.example.moneyAllocation.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.service.RegularTransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

class RegularTransferControllerTest {
    @Mock
    RegularTransferService service;

    @InjectMocks
    RegularTransferController target;

    private MockMvc mockMvc;

    private AutoCloseable mocks;

    @BeforeEach
    public void before() {
        mocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    @AfterEach
    public void after() throws Exception {
        mocks.close();
    }

    @Test
    void get() throws Exception {
        List<RegularTransfer> regularTransferList = new ArrayList<>();
        regularTransferList.add(new RegularTransfer());
        RegularTransferSelector selector = new RegularTransferSelector();

        ArgumentMatcher<RegularTransferSelector> matcher = argument -> {
            assertEquals(1L, argument.getUserId());
            return true;
        };
        Mockito.doReturn(regularTransferList).when(service).find(selector);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/regular/list")
                .queryParam("userId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(service, Mockito.times(1)).find(Mockito.argThat(matcher));
    }

    @Test
    void add() throws Exception{

        RegularTransfer regularTransfer = new RegularTransfer();
        regularTransfer.setId(1L);
        regularTransfer.setAmount(30000);

        ArgumentMatcher<RegularTransfer> matcher = argument -> {
            assertEquals(1L, argument.getId());
            assertEquals(30000, argument.getAmount());
            return true;
        };

        mockMvc.perform(MockMvcRequestBuilders.post("/api/regular")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonMaker.toJsonString(regularTransfer)));
        Mockito.verify(service, Mockito.times(1)).add(Mockito.argThat(matcher));
    }


    static class JsonMaker {
        private static ObjectMapper objectMapper;

        static {
            objectMapper = new ObjectMapper();
        }

        public static String toJsonString(Object o) throws Exception {
            return objectMapper.writeValueAsString(o);
        }
    }
}