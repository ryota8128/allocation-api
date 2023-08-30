package com.example.moneyAllocation.repository.impl;

import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.domain.UserSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.UserRepository;
import com.example.moneyAllocation.repository.mybatis.UserMapper;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private final SqlSession sqlSession;

  @Override
  public User find(UserSelector selector) {
    User user = sqlSession.getMapper(UserMapper.class).find(selector);
    if (user == null) {
      throw new ResourceNotFoundException("User not found");
    }
    return user;
  }

  @Override
  public void add(User user) {
    int affected = sqlSession.getMapper(UserMapper.class).add(user);
    if (affected != 1) {
      throw new RuntimeException("Userの追加に失敗しました．");
    }
  }

  @Override
  public void set(User user) {
    int affected = sqlSession.getMapper(UserMapper.class).update(user);
    if (affected != 1) {
      throw new ResourceNotFoundException("User not found");
    }
  }

  @Override
  public void delete(Long id) {
    int affected = sqlSession.getMapper(UserMapper.class).delete(id);
    if (affected != 1) {
      throw new ResourceNotFoundException("User not found");
    }
  }
}
