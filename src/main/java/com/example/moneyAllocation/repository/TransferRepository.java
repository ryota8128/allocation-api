package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import java.util.List;

public interface TransferRepository {
    List<Transfer> find(Long userId);

    Transfer findOne(TransferSelector selector);

    void add(Transfer transfer);

    void set(Transfer transfer);

    void delete(TransferSelector selector);
}
