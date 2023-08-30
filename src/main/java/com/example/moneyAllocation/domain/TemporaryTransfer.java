package com.example.moneyAllocation.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
  public List<AccountSelector> getSelectorList() {
    return Stream.of(fromAccount, toAccount)
        .filter(Objects::nonNull)
        .map(a -> AccountSelector.of(a, userId))
        .collect(Collectors.toList());
  }
}
