package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.mybatis.AccountMapper;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final SqlSession sqlSession;

    public AccountRepositoryImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<Account> find(AccountSelector selector) {
        return this.sqlSession.getMapper(AccountMapper.class).find(selector);
    }

    @Override
    public Account findOne(AccountSelector selector) {
        Account account = this.sqlSession.getMapper(AccountMapper.class).findOne(selector);
        if (account == null) {
            throw new ResourceNotFoundException("Account not found");
        }
        return account;
    }

    @Override
    public void add(Account account) {
        int affected = this.sqlSession.getMapper(AccountMapper.class).add(account);
        if (affected != 1) {
            throw new RuntimeException("データの追加に失敗しました．");
        }
    }

    @Override
    public void set(Account account) {
        int affected = this.sqlSession.getMapper(AccountMapper.class).set(account);
        if (affected != 1) {
            throw new ResourceNotFoundException("Account not found");
        }
    }

    @Override
    public void delete(AccountSelector selector) {
        int affected = this.sqlSession.getMapper(AccountMapper.class).delete(selector);
        if (affected != 1) {
            throw new ResourceNotFoundException("Account not found");
        }
    }
}
