package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.repository.AccountRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findList(Long ownerId) {
        return accountRepository.find(ownerId);
    }

    @Override
    public Account findOne(AccountSelector selector) {
        return accountRepository.findOne(selector);
    }

    @Override
    public void add(Account account) {
        accountRepository.add(account);
    }

    @Override
    public void set(Account account) {
        accountRepository.set(account);
    }

    @Transactional
    @Override
    public void delete(AccountSelector accountSelector) {
        accountRepository.delete(accountSelector);
    }
}
