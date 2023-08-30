package com.example.moneyAllocation.domain.service;

import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.domain.HaveToAndFromAccount;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.exception.ResourceValidationException;
import com.example.moneyAllocation.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TransferDomainService {
  private final AccountRepository accountRepository;

  public void checkValidAccounts(HaveToAndFromAccount transfer) {
    for (AccountSelector selector : transfer.getSelectorList()) {
      try {
        accountRepository.findOne(selector);
      } catch (ResourceNotFoundException e) {
        throw new ResourceValidationException("存在しない口座が指定されました", e);
      }
    }
  }
}
