package com.example.moneyAllocation.controller;


import com.example.moneyAllocation.domain.UserSelector;
import com.example.moneyAllocation.exception.HealthDieException;
import com.example.moneyAllocation.repository.UserRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@CrossOrigin(origins = {"http://localhost:3000"})
public class HealthController {
    private UserRepository repository;

    public HealthController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String get() {
        return "Backend APP OK";
    }

    @GetMapping("/db")
    public String health() {
        String username = "user1";
        UserSelector selector = new UserSelector();
        selector.setUsername(username);
        if (repository.find(selector) == null) {
            throw new HealthDieException("DBとの接続に失敗しました");
        }
        return "Database connection OK";
    }
}
