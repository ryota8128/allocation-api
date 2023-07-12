package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.exception.ResourceValidationException;
import com.example.moneyAllocation.repository.AccountRepository;
import com.example.moneyAllocation.repository.RegularTransferRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RegularTransferServiceImpl implements RegularTransferService {
    private final RegularTransferRepository transferRepository;
    private final AccountRepository accountRepository;

    public RegularTransferServiceImpl(RegularTransferRepository transferRepository,
                                      AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<RegularTransfer> find(RegularTransferSelector selector) {
        return this.transferRepository.find(selector);
    }

    @Override
    public RegularTransfer findOne(RegularTransferSelector selector) {
        return transferRepository.findOne(selector);
    }

    @Override
    public void add(RegularTransfer regularTransfer) {
        // percentのboolによってamountかratioが必須
        if (regularTransfer.getPercentage()) {
            if (regularTransfer.getRatio() == null) {
                throw new ResourceValidationException("ratioがnullになっています");
            }

            float ratio = regularTransfer.getRatio();
            if (ratio < 0 || ratio > 1) {
                throw new ResourceValidationException("ratioは0以上1以下で入力してください");
            }
        } else {
            if (regularTransfer.getAmount() == null) {
                throw new ResourceValidationException("amountがnullになっています");
            }
        }

        // fromAccountとtoAccountのownerIdはログインユーザのIDと一致してる必要あり
        for (Long accountId : Arrays.asList(regularTransfer.getFromAccount(), regularTransfer.getToAccount())) {
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

        transferRepository.add(regularTransfer);
    }

    @Override
    public void set(RegularTransfer regularTransfer) {
        transferRepository.set(regularTransfer);
    }

    @Override
    public void delete(Long id) {
        transferRepository.delete(id);
    }
}
