package com.example.moneyAllocation.controller;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.security.LoginUserDetails;
import com.example.moneyAllocation.service.AccountService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@CrossOrigin(origins = {"http://localhost:3000", "https://money-allocation.vercel.app"})
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> find(@AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        return service.findList(loginUserDetails.getLoginUser().id());
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account findOne(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestParam Long id) {
        AccountSelector selector = new AccountSelector();
        selector.setOwnerId(loginUserDetails.getLoginUser().id());
        selector.setId(id);
        return service.findOne(selector);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void add(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestBody Account account) {
        account.setOwnerId(loginUserDetails.getLoginUser().id());
        this.service.add(account);
    }

    @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void set(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestBody Account account) {
        account.setOwnerId(loginUserDetails.getLoginUser().id());
        this.service.set(account);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @PathVariable Long id) {
        AccountSelector selector = new AccountSelector();
        selector.setId(id);
        selector.setOwnerId(loginUserDetails.getLoginUser().id());
        this.service.delete(selector);
    }
}


