package com.example.moneyAllocation.controller;


import com.example.moneyAllocation.domain.Account;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@CrossOrigin(origins = {"http://localhost:3000"})
public class HealthController {
    @GetMapping
    public Account get() {
        Account account = new Account();
        account.setName("test");
        account.setOwnerId(99L);
        account.setId(99L);
        account.setNumFreeTransfer(1);
        account.setTransferFee(100);
        return account;
    }
}
