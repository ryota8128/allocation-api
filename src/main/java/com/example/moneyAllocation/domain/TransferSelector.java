package com.example.moneyAllocation.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class TransferSelector {
  private final Long userId;
  private final Long id;
  private final String title;
  private final Long transferId;

  public static TransferSelector withTitle(String title, Long userId) {
    return builder().title(title).userId(userId).build();
  }

  public static TransferSelector withId(Long id, Long userId) {
    return TransferSelector.builder().id(id).userId(userId).build();
  }

  public static TransferSelector withIdOrTitle(Long id, String title, Long userId) {
    return TransferSelector.builder().id(id).userId(userId).title(title).build();
  }

  public static TransferSelector withTransferId(Long transferId, Long userId) {
    return builder().transferId(transferId).userId(userId).build();
  }
}
