package com.example.moneyAllocation.repository.mybatis;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {
    List<Account> find(Long ownerId);

    Account findOne(AccountSelector selector);

    int add(Account account);

    int set(Account account);

    int delete(AccountSelector selector);

    void setNullToViaThatReferenceDeleteAccount(@Param("ownerId") Long ownerId, @Param("via") Long delAccountId);
}
