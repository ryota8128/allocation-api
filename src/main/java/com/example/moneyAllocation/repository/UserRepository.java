package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.domain.UserSelector;

public interface UserRepository {
    User find(UserSelector selector);

    void add(User user);

    void set(User user);

    void delete(Long id);
}
