package com.example.moneyAllocation.service.impl;

import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.exception.ResourceValidationException;
import com.example.moneyAllocation.repository.AccountRepository;
import com.example.moneyAllocation.repository.TemporaryTransferRepository;
import com.example.moneyAllocation.service.TemporaryTransferService;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TemporaryTransferServiceImpl implements TemporaryTransferService {
    private final TemporaryTransferRepository repository;

    private final AccountRepository accountRepository;

    public TemporaryTransferServiceImpl(TemporaryTransferRepository repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<TemporaryTransfer> find(TemporaryTransferSelector selector) {
        return repository.find(selector);
    }

    @Override
    public TemporaryTransfer findOne(TransferSelector selector) {
        return repository.findOne(selector);
    }

    @Override
    public void add(TemporaryTransfer temporaryTransfer) {
        for (Long accountId : Arrays.asList(temporaryTransfer.getFromAccount(), temporaryTransfer.getToAccount())) {
            if (accountId == null) continue;

            AccountSelector selector = new AccountSelector();
            selector.setId(accountId);
            selector.setOwnerId(temporaryTransfer.getUserId());
            try {
                // accountIdとOwnerIdを指定して取得できない口座がある場合は例外をスロー
                accountRepository.findOne(selector);
            } catch (ResourceNotFoundException e) {
                throw new ResourceValidationException("存在しない口座が指定されました");
            }
        }
        repository.add(temporaryTransfer);
    }

    @Override
    public void set(TemporaryTransfer temporaryTransfer) {
        for (Long accountId : Arrays.asList(temporaryTransfer.getFromAccount(), temporaryTransfer.getToAccount())) {
            if (accountId == null) continue;

            AccountSelector selector = new AccountSelector();
            selector.setId(accountId);
            selector.setOwnerId(temporaryTransfer.getUserId());
            try {
                // accountIdとOwnerIdを指定して取得できない口座がある場合は例外をスロー
                accountRepository.findOne(selector);
            } catch (ResourceNotFoundException e) {
                throw new ResourceValidationException("存在しない口座が指定されました");
            }
        }
        repository.set(temporaryTransfer);
    }

    @Override
    public void delete(TemporaryTransferSelector selector) {
        repository.delete(selector);
    }
}
