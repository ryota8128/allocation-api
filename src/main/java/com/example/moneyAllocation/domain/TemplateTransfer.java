package com.example.moneyAllocation.domain;

import com.example.moneyAllocation.domain.dto.TemplateTransferDto;
import lombok.Builder;

@Builder
public class TemplateTransfer {
  private final Long id;
  private final Long fromAccount;
  private final Long toAccount;
  private final String description;
  private final Long userId;

  public TemplateTransfer fromDto(TemplateTransferDto templateTransferDto) {
    return TemplateTransfer.builder()
        .id(templateTransferDto.getId())
        .fromAccount(templateTransferDto.getFromAccount())
        .toAccount(templateTransferDto.getToAccount())
        .description(templateTransferDto.getDescription())
        .userId(templateTransferDto.getUserId())
        .build();
  }
}
