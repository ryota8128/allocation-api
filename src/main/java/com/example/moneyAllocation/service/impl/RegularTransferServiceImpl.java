package com.example.moneyAllocation.service.impl;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.domain.service.TransferDomainService;
import com.example.moneyAllocation.repository.RegularTransferRepository;
import com.example.moneyAllocation.service.RegularTransferService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegularTransferServiceImpl implements RegularTransferService {
  private final RegularTransferRepository regularTransferRepository;
  private final TransferDomainService transferDomainService;

  @Override
  public List<RegularTransfer> find(Long userId) {
    return this.regularTransferRepository.find(userId);
  }

  @Override
  public RegularTransfer findOne(TransferSelector selector) {
    return regularTransferRepository.findOne(selector);
  }

  @Override
  public RegularTransfer add(RegularTransfer regularTransfer) {
    regularTransfer.setZero();
    regularTransfer.checkRatioValid();
    transferDomainService.checkValidAccounts(regularTransfer);
    Long id = regularTransferRepository.add(regularTransfer);
    return regularTransferRepository.findOne(
        TransferSelector.withId(id, regularTransfer.getUserId()));
  }

  @Override
  public void set(RegularTransfer regularTransfer) {
    regularTransfer.setZero();
    regularTransfer.checkRatioValid();
    transferDomainService.checkValidAccounts(regularTransfer);
    regularTransferRepository.set(regularTransfer);
  }

  @Override
  public void delete(TransferSelector selector) {
    regularTransferRepository.delete(selector);
  }
}
