package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import java.util.List;

public interface RegularTransferService {
    List<RegularTransfer> find(Long userId);
    RegularTransfer findOne(TransferSelector selector);

    void add(RegularTransfer regularTransfer);

    void set(RegularTransfer regularTransfer);

    void delete(TransferSelector selector);
}
