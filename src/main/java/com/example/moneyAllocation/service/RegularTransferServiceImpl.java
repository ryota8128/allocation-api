package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.repository.RegularTransferRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RegularTransferServiceImpl implements RegularTransferService{
    private final RegularTransferRepository repository;

    public RegularTransferServiceImpl(RegularTransferRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RegularTransfer> find(RegularTransferSelector selector) {
        return  this.repository.find(selector);
    }

    @Override
    public void add(RegularTransfer regularTransfer) {

    }

    @Override
    public void set(RegularTransfer regularTransfer) {

    }

    @Override
    public void delete(Long id) {

    }
}
