package com.example.moneyAllocation.domain;

import com.example.moneyAllocation.domain.dto.TemplateTransferDto;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public class TemplateTransfer {
  private final Long id;
  private final Long fromAccount;
  private final Long toAccount;
  private final String fromAccountName;
  private final String toAccountName;
  private final String description;
  private final Long userId;

  public static TemplateTransfer fromDto(TemplateTransferDto templateTransferDto) {
    return TemplateTransfer.builder()
        .id(templateTransferDto.getId())
        .fromAccount(templateTransferDto.getFromAccount())
        .toAccount(templateTransferDto.getToAccount())
        .fromAccountName(templateTransferDto.getFromAccountName())
        .toAccountName(templateTransferDto.getToAccountName())
        .description(templateTransferDto.getDescription())
        .userId(templateTransferDto.getUserId())
        .build();
  }
}
