package com.example.moneyAllocation.domain;

import com.example.moneyAllocation.exception.ResourceValidationException;
import java.util.List;
import java.util.Objects;
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
  public List<AccountSelector> fetchSelectorList() {
    return Stream.of(fromAccount, toAccount)
        .filter(Objects::nonNull)
        .map(a -> AccountSelector.of(a, userId))
        .collect(Collectors.toList());
  }

  public void checkRatioValid() {
    if (ratio < 0 || ratio > 1) {
      throw new ResourceValidationException("ratioは0以上1以下で入力してください");
    }
  }

  public void setZero() {
    if (ratio == null) {
      ratio = 0F;
    }

    if (amount == null) {
      amount = 0;
    }
  }
}
