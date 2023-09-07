package com.example.moneyAllocation.domain;

import com.example.moneyAllocation.domain.dto.TemporaryTransferDto;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
public class TemporaryTransfer implements HaveToAndFromAccount {
  private Long id;
  private Long fromAccount;
  private Long toAccount;
  private String fromAccountName;
  private String toAccountName;
  private String description;
  private Integer amount;
  private Long userId;
  private Long transferId;

  @Override
  public List<AccountSelector> fetchSelectorList() {
    return Stream.of(fromAccount, toAccount)
        .filter(Objects::nonNull)
        .map(a -> AccountSelector.of(a, userId))
        .collect(Collectors.toList());
  }

  public static TemporaryTransfer of(TemplateTransfer templateTransfer, Long transferId) {
    return builder()
        .fromAccount(templateTransfer.getFromAccount())
        .toAccount(templateTransfer.getToAccount())
        .description(templateTransfer.getDescription())
        .amount(0)
        .userId(templateTransfer.getUserId())
        .transferId(transferId)
        .build();
  }

  public static TemporaryTransfer from(TemporaryTransferDto dto) {
    return builder()
        .id(dto.getId())
        .fromAccount(dto.getFromAccount())
        .toAccount(dto.getToAccount())
        .amount(dto.getAmount())
        .description(dto.getDescription())
        .userId(dto.getUserId())
        .transferId(dto.getTransferId())
        .build();
  }
}
