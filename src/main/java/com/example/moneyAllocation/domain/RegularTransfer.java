package com.example.moneyAllocation.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegularTransfer implements HaveToAndFromAccount {
  private Long id;
  private Long fromAccount;
  private Long toAccount;
  private String fromAccountName;
  private String toAccountName;
  private String description;
  private Boolean percentage;
  private Integer amount;
  private Float ratio;
  private Long userId;

  @Override
  public List<AccountSelector> getSelectorList() {
    return Stream.of(fromAccount, toAccount)
        .map(a -> AccountSelector.of(a, userId))
        .collect(Collectors.toList());
  }
}
