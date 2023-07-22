package com.example.moneyAllocation.service;


import com.example.moneyAllocation.domain.Transfer;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {
    @Override
    public List<Transfer> find(Long userId) {
        return null;
    }

    @Override
    public Transfer findOne(Long transferId) {
        return null;
    }

    @Override
    public void add(Transfer transfer) {

    }

    @Override
    public void set(Transfer transfer) {

    }

    @Override
    public void delete(Long transferId) {

    }
}
