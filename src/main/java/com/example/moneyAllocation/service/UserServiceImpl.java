package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.domain.UserSelector;
import com.example.moneyAllocation.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User find(UserSelector selector) {
        return repository.find(selector);
    }

    @Override
    public void add(User user) {
        repository.add(user);
    }

    @Override
    public void set(User user) {
        repository.set(user);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
