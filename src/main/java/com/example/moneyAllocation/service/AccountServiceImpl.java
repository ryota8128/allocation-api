package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.repository.AccountRepository;
import com.example.moneyAllocation.repository.RegularTransferRepository;
import com.example.moneyAllocation.repository.TemporaryTransferRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RegularTransferRepository regularTransferRepository;
    private final TemporaryTransferRepository temporaryTransferRepository;

    public AccountServiceImpl(AccountRepository accountRepository, RegularTransferRepository regularTransferRepository,
                              TemporaryTransferRepository temporaryTransferRepository) {
        this.accountRepository = accountRepository;
        this.regularTransferRepository = regularTransferRepository;
        this.temporaryTransferRepository = temporaryTransferRepository;
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
        regularTransferRepository.setNullAccount(accountSelector.getId());
        temporaryTransferRepository.setNullAccount(accountSelector.getId());
        accountRepository.delete(accountSelector);
    }
}
