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
  public void add(RegularTransfer regularTransfer) {
    // TODO: リファクタ
    if (regularTransfer.getRatio() == null) {
      regularTransfer.setRatio(0F);
    }

    // TODO: リファクタ
    if (regularTransfer.getAmount() == null) {
      regularTransfer.setAmount(0);
    }

    regularTransfer.checkRatioValid();

    transferDomainService.checkValidAccounts(regularTransfer);
    regularTransferRepository.add(regularTransfer);
  }

  @Override
  public void set(RegularTransfer regularTransfer) {
    // TODO: リファクタ
    if (regularTransfer.getRatio() == null) {
      regularTransfer.setRatio(0F);
    }

    // TODO: リファクタ
    if (regularTransfer.getAmount() == null) {
      regularTransfer.setAmount(0);
    }

    regularTransfer.checkRatioValid();
    transferDomainService.checkValidAccounts(regularTransfer);

    regularTransferRepository.set(regularTransfer);
  }

  @Override
  public void delete(TransferSelector selector) {
    regularTransferRepository.delete(selector);
  }
}
