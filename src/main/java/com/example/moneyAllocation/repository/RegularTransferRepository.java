package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import java.util.List;

public interface RegularTransferRepository {
    List<RegularTransfer> find(Long userId);

    RegularTransfer findOne(TransferSelector selector);

    Long add(RegularTransfer regularTransfer);

    void set(RegularTransfer regularTransfer);

    void delete(TransferSelector selector);

    void setNullAccount(Long accountId);
}
