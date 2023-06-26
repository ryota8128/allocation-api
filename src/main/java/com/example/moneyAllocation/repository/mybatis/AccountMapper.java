package com.example.moneyAllocation.repository.mybatis;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    List<Account> find(AccountSelector selector);
}