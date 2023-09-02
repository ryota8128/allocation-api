package com.example.moneyAllocation.service.impl;

import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.domain.TemplateTransferList;
import com.example.moneyAllocation.domain.service.TransferDomainService;
import com.example.moneyAllocation.exception.ResourceValidationException;
import com.example.moneyAllocation.repository.TemplateTransferRepository;
import com.example.moneyAllocation.service.TemplateTransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TemplateTransferServiceImpl implements TemplateTransferService {
  private final TemplateTransferRepository templateTransferRepository;

  private final TransferDomainService transferDomainService;

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
    transferDomainService.checkValidAccounts(templateTransfer);
    if (!templateTransfer.isValidDescription()) {
      throw new ResourceValidationException("descriptionを入力してください");
    }
    templateTransferRepository.insert(templateTransfer);
  }

  @Override
  public void update(TemplateTransfer templateTransfer) {
    transferDomainService.checkValidAccounts(templateTransfer);
    templateTransferRepository.update(templateTransfer);
  }

  @Override
  public void delete(Long id, Long userId) {
    templateTransferRepository.delete(id, userId);
  }
}
