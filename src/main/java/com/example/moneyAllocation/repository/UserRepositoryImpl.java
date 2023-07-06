package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.domain.UserSelector;
import com.example.moneyAllocation.repository.mybatis.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SqlSession sqlSession;

    public UserRepositoryImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

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
