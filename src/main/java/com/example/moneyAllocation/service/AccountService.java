package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.repository.AccountRepository;
import java.util.List;
import org.springframework.stereotype.Service;


public interface AccountService {
    List<Account> findList(AccountSelector selector);


}
