package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import java.util.List;

public interface TemporaryTransferService {
    List<TemporaryTransfer> find(Long userId);

    TemporaryTransfer findOne(TransferSelector selector);

    void add(TemporaryTransfer temporaryTransfer);

    void set(TemporaryTransfer temporaryTransfer);

    void delete(TransferSelector selector);
}
