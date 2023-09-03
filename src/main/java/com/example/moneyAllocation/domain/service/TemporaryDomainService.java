package com.example.moneyAllocation.domain.service;

import com.example.moneyAllocation.domain.TemplateTransferList;

public interface TemporaryDomainService {
  void insertTemplateList(TemplateTransferList templateTransferList, Long transferId);
}
