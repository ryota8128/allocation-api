package com.example.moneyAllocation.service.impl;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.exception.BudRequestException;
import com.example.moneyAllocation.repository.AccountRepository;
import com.example.moneyAllocation.service.AccountService;
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
    if (isInvalidViaId(account)) {
      throw new BudRequestException("不正な振込経由地が指定されました");
    }
    accountRepository.add(account);
  }

  @Override
  public void set(Account account) {
    if (isInvalidViaId(account)) {
      throw new BudRequestException("不正な振込経由地が指定されました");
    }
    accountRepository.set(account);
  }

  @Transactional
  @Override
  public void delete(AccountSelector accountSelector) {
    accountRepository.delete(accountSelector);
  }

  public boolean isInvalidViaId(Account account) {
    if (account.getVia() == null) {
      // viaの指定がなければ問題なし
      return false;
    }

    if (account.getOwnerId() == null) {
      return true;
    }

    // ownerId, viaのaccountが存在するかチェック
    AccountSelector selector = new AccountSelector(account.getOwnerId(), account.getVia(), null);
    return accountRepository.findOne(selector) == null;
  }
}
