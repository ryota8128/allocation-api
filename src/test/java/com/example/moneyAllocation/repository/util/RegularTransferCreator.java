package com.example.moneyAllocation.repository.util;

import com.example.moneyAllocation.domain.RegularTransfer;

public class RegularTransferCreator {
    public static RegularTransfer regularCreate(
            Long id, Long fromAccount, Long toAccount, Boolean percentage,
            Integer amount, Float ratio, String description, Long userId) {

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

}
