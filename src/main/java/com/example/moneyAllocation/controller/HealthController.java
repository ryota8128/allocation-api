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
}
