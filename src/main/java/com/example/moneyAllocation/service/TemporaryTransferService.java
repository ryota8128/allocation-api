package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.domain.TransferSelector;
import java.util.List;

public interface TemporaryTransferService {
    List<TemporaryTransfer> find(TemporaryTransferSelector selector);

    TemporaryTransfer findOne(TransferSelector selector);

    void add(TemporaryTransfer temporaryTransfer);

    void set(TemporaryTransfer temporaryTransfer);

    void delete(TemporaryTransferSelector selector);
}
