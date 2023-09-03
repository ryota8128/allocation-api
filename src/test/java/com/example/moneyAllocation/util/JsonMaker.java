package com.example.moneyAllocation.util;


import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMaker {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static String toJsonString(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }
}
