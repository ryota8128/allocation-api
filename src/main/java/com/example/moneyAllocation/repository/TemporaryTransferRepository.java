package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import java.util.List;

public interface TemporaryTransferRepository {
    List<TemporaryTransfer> find(TemporaryTransferSelector selector);

    TemporaryTransfer findOne(TemporaryTransferSelector selector);

    void add(TemporaryTransfer temporaryTransfer);

    void set(TemporaryTransfer temporaryTransfer);

    void delete(Long id);

    void setNullAccount(Long accountId);
}
