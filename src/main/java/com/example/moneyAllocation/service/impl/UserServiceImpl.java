package com.example.moneyAllocation.service.impl;

import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.domain.UserSelector;
import com.example.moneyAllocation.repository.UserRepository;
import com.example.moneyAllocation.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository repository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public User find(UserSelector selector) {
    return repository.find(selector);
  }

  @Override
  public void add(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
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
