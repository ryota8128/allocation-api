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
    public RegularTransfer findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    public void add(RegularTransfer regularTransfer) {
        repository.add(regularTransfer);
    }

    @Override
    public void set(RegularTransfer regularTransfer) {
        repository.set(regularTransfer);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
