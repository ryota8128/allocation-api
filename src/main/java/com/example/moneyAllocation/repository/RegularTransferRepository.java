package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import java.util.List;

public interface RegularTransferRepository {
    List<RegularTransfer> find(Long userId);

    RegularTransfer findOne(RegularTransferSelector selector);

    void add(RegularTransfer regularTransfer);

    void set(RegularTransfer regularTransfer);

    void delete(RegularTransferSelector selector);

    void setNullAccount(Long accountId);
}
