package com.example.moneyAllocation.domain;

public class TemporaryTransferSelector extends TransferSelector {
    private Long transferId;

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }
}
