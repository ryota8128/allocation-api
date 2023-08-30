package com.example.moneyAllocation.service;

import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.domain.TemplateTransferList;

public interface TemplateTransferService {
    TemplateTransferList find(Long userId);

    TemplateTransfer findOne(Long id, Long userId);

    void insert(TemplateTransfer templateTransfer);

    void update(TemplateTransfer templateTransfer);

    void delete(Long id, Long userId);
}
