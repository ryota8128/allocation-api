package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import java.util.List;

public interface RegularTransferService {
    List<RegularTransfer> find(RegularTransferSelector selector);
    RegularTransfer findOne(RegularTransferSelector selector);

    void add(RegularTransfer regularTransfer);

    void set(RegularTransfer regularTransfer);

    void delete(Long id);
}
