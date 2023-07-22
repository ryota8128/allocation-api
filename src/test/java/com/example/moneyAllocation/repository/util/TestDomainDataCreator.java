package com.example.moneyAllocation.repository.util;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.TemporaryTransfer;

public class TestDomainDataCreator {
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

    public static TemporaryTransfer temporaryCreate(
            Long id, Long fromAccount, Long toAccount, Integer amount,
            String description, Long userId, Long transferId) {
        TemporaryTransfer temporaryTransfer = new TemporaryTransfer();
        temporaryTransfer.setId(id);
        temporaryTransfer.setFromAccount(fromAccount);
        temporaryTransfer.setToAccount(toAccount);
        temporaryTransfer.setAmount(amount);
        temporaryTransfer.setDescription(description);
        temporaryTransfer.setUserId(userId);
        temporaryTransfer.setTransferId(transferId);

        return temporaryTransfer;
    }



}
