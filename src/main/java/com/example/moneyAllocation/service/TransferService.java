package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.Transfer;
import java.util.List;

public interface TransferService {
    List<Transfer> find(Long userId);

    Transfer findOne(Long transferId);

    void add(Transfer transfer);

    void set(Transfer transfer);

    void delete(Long transferId);
}
