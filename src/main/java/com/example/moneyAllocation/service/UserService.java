package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.domain.UserSelector;

public interface UserService {
    User find(UserSelector selector);

    void add(User user);

    void set(User user);

    void delete(Long id);
}
