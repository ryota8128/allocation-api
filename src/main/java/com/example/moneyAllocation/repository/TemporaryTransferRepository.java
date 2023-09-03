package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import java.util.List;

public interface TemporaryTransferRepository {
    List<TemporaryTransfer> find(TransferSelector selector);

    TemporaryTransfer findOne(TransferSelector selector);

    void add(TemporaryTransfer temporaryTransfer);

    void set(TemporaryTransfer temporaryTransfer);

    void delete(TransferSelector selector);

    void setNullAccount(Long accountId);
}
