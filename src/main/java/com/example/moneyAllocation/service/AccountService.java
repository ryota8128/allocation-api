package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import java.util.List;

public interface AccountService {
    List<Account> findList(AccountSelector selector);

    Account findOne(AccountSelector selector);

    void add(Account account);

    void set(Account account);

    void delete(AccountSelector selector);
}
