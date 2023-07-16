package com.example.moneyAllocation.controller;


import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.domain.UserSelector;
import com.example.moneyAllocation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");
        String dbHost = System.getenv("DB_HOST");
        String dbPort = System.getenv("DB_PORT");
        String dbSchema = System.getenv("DB_SCHEMA");
        StringBuilder sb = new StringBuilder(dbUser);
        sb.append("\n" + dbPass).append("\n" + dbHost).append("\n" + dbPort).append("\n" + dbSchema);
        return sb.toString();
    }

    @GetMapping("/db")
    public User getUser() {
        String username = "user1";
        UserSelector selector = new UserSelector();
        selector.setUsername(username);
        return repository.find(selector);
    }
}
