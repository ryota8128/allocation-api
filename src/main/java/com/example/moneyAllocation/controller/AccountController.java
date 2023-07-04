package com.example.moneyAllocation.controller;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.service.AccountService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> find(@RequestParam(required = false) Long ownerId) {
        AccountSelector selector = new AccountSelector();
        selector.setOwnerId(ownerId);
        return service.findList(selector);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestBody Account account) {
        this.service.add(account);
    }

    @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void set(@RequestBody Account account) {
        this.service.set(account);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }
}


