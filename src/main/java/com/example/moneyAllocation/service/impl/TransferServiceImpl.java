package com.example.moneyAllocation.service.impl;


import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.repository.TransferRepository;
import com.example.moneyAllocation.service.TransferService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;

    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public List<Transfer> find(Long userId) {
        return transferRepository.find(userId);
    }

    @Override
    public Transfer findOne(TransferSelector selector) {
        return transferRepository.findOne(selector);
    }


    @Override
    public void add(Transfer transfer) {
        transferRepository.add(transfer);
    }

    @Override
    public void set(Transfer transfer) {
        transferRepository.set(transfer);
    }

    @Override
    public void delete(TransferSelector selector) {
        transferRepository.delete(selector);
    }
}
