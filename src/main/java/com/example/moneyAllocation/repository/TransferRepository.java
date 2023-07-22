package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.Transfer;
import java.util.List;

public interface TransferRepository {
    List<Transfer> find(Long userId);

    Transfer findOne(Long transferId);

    void add(Transfer transfer);

    void set(Transfer transfer);

    void delete(Long transferId);
}
