package com.example.moneyAllocation.domain;

import com.example.moneyAllocation.domain.dto.TemplateTransferDto;
import com.example.moneyAllocation.exception.BudRequestException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class TemplateTransfer {
  private final Long id;
  private final Long fromAccount;
  private final Long toAccount;
  private final String fromAccountName;
  private final String toAccountName;
  private final String description;
  private final Long userId;

  public List<AccountSelector> getSelectorList() {
    if (fromAccount == null || toAccount == null) {
      throw new BudRequestException("fromまたはtoがnullです．");
    }
    return Stream.of(fromAccount, toAccount)
        .map(a -> AccountSelector.of(a, userId))
        .collect(Collectors.toList());
  }

  public static TemplateTransfer from(TemplateTransferDto templateTransferDto) {
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
