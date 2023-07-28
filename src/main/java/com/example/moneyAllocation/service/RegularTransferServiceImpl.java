package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.exception.ResourceValidationException;
import com.example.moneyAllocation.repository.AccountRepository;
import com.example.moneyAllocation.repository.RegularTransferRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RegularTransferServiceImpl implements RegularTransferService {
    private final RegularTransferRepository regularTransferRepository;
    private final AccountRepository accountRepository;

    public RegularTransferServiceImpl(RegularTransferRepository regularTransferRepository,
                                      AccountRepository accountRepository) {
        this.regularTransferRepository = regularTransferRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<RegularTransfer> find(Long userId) {
        return this.regularTransferRepository.find(userId);
    }

    @Override
    public RegularTransfer findOne(TransferSelector selector) {
        return regularTransferRepository.findOne(selector);
    }

    @Override
    public void add(RegularTransfer regularTransfer) {

        if (regularTransfer.getRatio() == null) {
            regularTransfer.setRatio(0F);
        }

        float ratio = regularTransfer.getRatio();
        if (ratio < 0 || ratio > 1) {
            throw new ResourceValidationException("ratioは0以上1以下で入力してください");
        }

        if (regularTransfer.getAmount() == null) {
            regularTransfer.setAmount(0);
        }

        // fromAccountとtoAccountのownerIdはログインユーザのIDと一致してる必要あり
        for (Long accountId : Arrays.asList(regularTransfer.getFromAccount(), regularTransfer.getToAccount())) {
            if (accountId == null) continue;

            AccountSelector selector = new AccountSelector();
            selector.setId(accountId);
            selector.setOwnerId(regularTransfer.getUserId());
            try {
                // accountIdとOwnerIdを指定して取得できない口座がある場合は例外をスロー
                accountRepository.findOne(selector);
            } catch (ResourceNotFoundException e) {
                throw new ResourceValidationException("存在しない口座が指定されました");
            }
        }

        regularTransferRepository.add(regularTransfer);
    }

    @Override
    public void set(RegularTransfer regularTransfer) {
        if (regularTransfer.getRatio() == null) {
            regularTransfer.setRatio(0F);
        }

        float ratio = regularTransfer.getRatio();
        if (ratio < 0 || ratio > 1) {
            throw new ResourceValidationException("ratioは0以上1以下で入力してください");
        }

        if (regularTransfer.getAmount() == null) {
            regularTransfer.setAmount(0);
        }


        // fromAccountとtoAccountのownerIdはログインユーザのIDと一致してる必要あり
        for (Long accountId : Arrays.asList(regularTransfer.getFromAccount(), regularTransfer.getToAccount())) {
            if (accountId == null) {continue;}

            AccountSelector selector = new AccountSelector();
            selector.setId(accountId);
            selector.setOwnerId(regularTransfer.getUserId());
            try {
                // accountIdとOwnerIdを指定して取得できない口座がある場合は例外をスロー
                accountRepository.findOne(selector);
            } catch (ResourceNotFoundException e) {
                throw new ResourceValidationException("入力データに不備があります");
            }
        }

        regularTransferRepository.set(regularTransfer);
    }

    @Override
    public void delete(TransferSelector selector) {
        regularTransferRepository.delete(selector);
    }
}
