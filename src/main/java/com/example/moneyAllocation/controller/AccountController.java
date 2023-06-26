package com.example.moneyAllocation.controller;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.repository.AccountRepository;
import com.example.moneyAllocation.service.AccountService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> index() {
        return service.findList();
    }
}


