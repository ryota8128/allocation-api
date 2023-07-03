package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import java.util.List;

public interface RegularTransferRepository {
    List<RegularTransfer> find(RegularTransferSelector selector);

    void add(RegularTransfer regularTransfer);

    void set(RegularTransfer regularTransfer);

    void delete(Long id);
}
