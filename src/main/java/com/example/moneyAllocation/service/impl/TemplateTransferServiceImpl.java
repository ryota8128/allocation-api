package com.example.moneyAllocation.service.impl;

import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.domain.TemplateTransferList;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.exception.ResourceValidationException;
import com.example.moneyAllocation.repository.AccountRepository;
import com.example.moneyAllocation.repository.TemplateTransferRepository;
import com.example.moneyAllocation.service.TemplateTransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TemplateTransferServiceImpl implements TemplateTransferService {
  private final TemplateTransferRepository templateTransferRepository;
  private final AccountRepository accountRepository;

  @Override
  public TemplateTransferList find(Long userId) {
    return templateTransferRepository.find(userId);
  }

  @Override
  public TemplateTransfer findOne(Long id, Long userId) {
    return templateTransferRepository.findOne(id, userId);
  }

  @Override
  public void insert(TemplateTransfer templateTransfer) {
    // 指定されたaccount(from, to)が自身の口座かを確認
    for (AccountSelector selector : templateTransfer.getSelectorList()) {
      try {
        accountRepository.findOne(selector);
      } catch (ResourceNotFoundException e) {
        throw new ResourceValidationException("存在しない口座が指定されました");
      }
    }

    templateTransferRepository.insert(templateTransfer);
  }
}
