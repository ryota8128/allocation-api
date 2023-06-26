package com.example.moneyAllocation.controller;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.repository.AccountRepository;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final AccountRepository repository;

    public ApiController(AccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> index() {

        return repository.find();
    }
}


