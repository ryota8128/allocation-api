package com.example.moneyAllocation.controller;


import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.domain.UserSelector;
import com.example.moneyAllocation.exception.UserRegisterException;
import com.example.moneyAllocation.security.JwtResponse;
import com.example.moneyAllocation.security.JwtUtils;
import com.example.moneyAllocation.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    private final JwtUtils jwtUtils;

    public UserController(UserService service, JwtUtils jwtUtils) {
        this.service = service;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@RequestParam(required = true) String username) {
        UserSelector selector = new UserSelector();
        selector.setUsername(username);
        return service.find(selector);
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setAdministratorFlag(false);
        try {
            service.add(user);
        } catch (Exception e) {
            throw new UserRegisterException("新規ユーザの登録に失敗しました");
        }

        final String jwt = jwtUtils.generateToken(user.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt));
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
