package com.example.moneyAllocation.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(access = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSelector {

  private Long ownerId;

  private Long id;

  private String name;

  public static AccountSelector of(Long accountId, Long ownerId) {
    return builder().id(accountId).ownerId(ownerId).build();
  }
}
