package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.domain.TemplateTransferList;

public interface ITemplateTransferRepository {
  TemplateTransferList find(Long userId);

  TemplateTransfer findOne(Long id, Long userId);
}
