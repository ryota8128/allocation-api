package com.example.moneyAllocation.service.impl;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.domain.service.TransferDomainService;
import com.example.moneyAllocation.repository.TemporaryTransferRepository;
import com.example.moneyAllocation.service.TemporaryTransferService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemporaryTransferServiceImpl implements TemporaryTransferService {
  private final TemporaryTransferRepository repository;
  private final TransferDomainService transferDomainService;

  @Override
  public List<TemporaryTransfer> find(TransferSelector selector) {
    return repository.find(selector);
  }

  @Override
  public TemporaryTransfer findOne(TransferSelector selector) {
    return repository.findOne(selector);
  }

  @Override
  public void add(TemporaryTransfer temporaryTransfer) {
    transferDomainService.checkValidAccounts(temporaryTransfer);
    repository.add(temporaryTransfer);
  }

  @Override
  public void set(TemporaryTransfer temporaryTransfer) {
    transferDomainService.checkValidAccounts(temporaryTransfer);
    repository.set(temporaryTransfer);
  }

  @Override
  public void delete(TransferSelector selector) {
    repository.delete(selector);
  }
}
