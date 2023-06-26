package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.Account;
import java.util.List;

public interface AccountRepository {
    List<Account> find();
}
