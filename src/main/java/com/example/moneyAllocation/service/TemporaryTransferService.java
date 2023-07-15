package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import java.util.List;

public interface TemporaryTransferService {
    List<TemporaryTransfer> find(TemporaryTransferSelector selector);

    TemporaryTransfer findOne(TemporaryTransferSelector selector);

    void add(TemporaryTransfer temporaryTransfer);

    void set(TemporaryTransfer temporaryTransfer);

    void delete(Long id);
}
