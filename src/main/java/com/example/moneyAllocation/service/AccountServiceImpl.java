package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.repository.AccountRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public void add(Account account) {
        accountRepository.add(account);
    }

    @Override
    public List<Account> findList(AccountSelector selector) {
        return accountRepository.find(selector);
    }

    @Override
    public void add(Account account) {
        accountRepository.add(account);
    }

    @Override
    public void set(Account account) {
        accountRepository.set(account);
    }

    @Override
    public void delete(Long id) {
        accountRepository.delete(id);
    }
}
