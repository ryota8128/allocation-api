package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.domain.TemplateTransferList;

public interface TemplateTransferRepository {
  TemplateTransferList find(Long userId);

  TemplateTransfer findOne(Long id, Long userId);

  Long insert(TemplateTransfer templateTransfer);

  void update(TemplateTransfer templateTransfer);

  void delete(Long id, Long userId);

  void setNullAccount(Long accountId);
}
