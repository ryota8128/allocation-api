package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.repository.TemporaryTransferRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TemporaryTransferServiceImpl implements TemporaryTransferService{
    private final TemporaryTransferRepository repository;

    public TemporaryTransferServiceImpl(TemporaryTransferRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TemporaryTransfer> find(TemporaryTransferSelector selector) {
        return repository.find(selector);
    }

    @Override
    public TemporaryTransfer findOne(TemporaryTransferSelector selector) {
        return repository.findOne(selector);
    }

    @Override
    public void add(TemporaryTransfer temporaryTransfer) {
        repository.add(temporaryTransfer);
    }

    @Override
    public void set(TemporaryTransfer temporaryTransfer) {
        repository.set(temporaryTransfer);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
