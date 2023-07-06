package com.example.moneyAllocation.controller;


import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.domain.UserSelector;
import com.example.moneyAllocation.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@RequestParam(required = true) String email) {
        UserSelector selector = new UserSelector();
        selector.setEmail(email);
        return service.find(selector);
    }

    @PostMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public void insert(@RequestBody User user) {
        user.setAdministratorFlag(false);
        service.add(user);
    }

    @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        user.setAdministratorFlag(false);
        service.set(user);
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestParam(required = true) Long id) {
        service.delete(id);
    }

}
