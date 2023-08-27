package com.example.moneyAllocation.service.impl;

import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.domain.TemplateTransferList;
import com.example.moneyAllocation.repository.TemplateTransferRepository;
import com.example.moneyAllocation.service.TemplateTransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TemplateTransferServiceImpl implements TemplateTransferService {
  private final TemplateTransferRepository repository;

  @Override
  public TemplateTransferList find(Long userId) {
    return repository.find(userId);
  }

  @Override
  public TemplateTransfer findOne(Long id, Long userId) {
    return repository.findOne(id, userId);
  }
}
