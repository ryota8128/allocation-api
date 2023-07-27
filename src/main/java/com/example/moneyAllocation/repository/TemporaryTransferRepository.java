package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.domain.TransferSelector;
import java.util.List;

public interface TemporaryTransferRepository {
    List<TemporaryTransfer> find(TemporaryTransferSelector selector);

    TemporaryTransfer findOne(TransferSelector selector);

    void add(TemporaryTransfer temporaryTransfer);

    void set(TemporaryTransfer temporaryTransfer);

    void delete(TemporaryTransferSelector selector);

    void setNullAccount(Long accountId);
}
