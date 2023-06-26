package com.example.moneyAllocation.controller;

import com.example.moneyAllocation.domain.Account;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account index() {
        Account account = new Account();
        account.setId(1L);
        account.setName("三井住友");
        return account;
    }
}


