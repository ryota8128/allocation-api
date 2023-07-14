package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import java.util.List;

public interface AccountRepository {
    List<Account> find(AccountSelector selector);

    Account findOne(AccountSelector selector);
    void add(Account account);

    void set(Account account);

    void delete(AccountSelector selector);
}
