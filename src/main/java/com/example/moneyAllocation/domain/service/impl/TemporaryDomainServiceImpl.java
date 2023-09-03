package com.example.moneyAllocation.domain.service.impl;

import com.example.moneyAllocation.domain.TemplateTransferList;
import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.service.TemporaryDomainService;
import com.example.moneyAllocation.repository.TemporaryTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TemporaryDomainServiceImpl implements TemporaryDomainService {
  private final TemporaryTransferRepository temporaryTransferRepository;

  @Override
  public void insertTemplateList(TemplateTransferList templateTransferList, Long transferId) {
    templateTransferList.getList()
        .forEach(t -> temporaryTransferRepository.add(TemporaryTransfer.of(t, transferId)));
  }
}
