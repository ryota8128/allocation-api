package com.example.moneyAllocation.util;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.dto.TemporaryTransferDto;

public class TestDomainDataCreator {
  public static RegularTransfer regularCreate(
      Long id,
      Long fromAccount,
      Long toAccount,
      Boolean percentage,
      Integer amount,
      Float ratio,
      String description,
      Long userId) {

    RegularTransfer regularTransfer = new RegularTransfer();
    regularTransfer.setId(id);
    regularTransfer.setFromAccount(fromAccount);
    regularTransfer.setToAccount(toAccount);
    regularTransfer.setPercentage(percentage);
    regularTransfer.setAmount(amount);
    regularTransfer.setRatio(ratio);
    regularTransfer.setDescription(description);
    regularTransfer.setUserId(userId);
    return regularTransfer;
  }

  public static TemporaryTransfer temporaryCreate(
      Long id,
      Long fromAccount,
      Long toAccount,
      Integer amount,
      String description,
      Long userId,
      Long transferId) {
    TemporaryTransferDto dto =
        TemporaryTransferDto.builder()
            .id(id)
            .fromAccount(fromAccount)
            .toAccount(toAccount)
            .amount(amount)
            .description(description)
            .userId(userId)
            .transferId(transferId)
            .build();
    return TemporaryTransfer.from(dto);
  }
}
