package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import java.util.List;

public interface TransferService {
    List<Transfer> find(Long userId);

    Transfer findOne(TransferSelector selector);

    Transfer add(Transfer transfer);

    void set(Transfer transfer);

    void delete(TransferSelector selector);
}
