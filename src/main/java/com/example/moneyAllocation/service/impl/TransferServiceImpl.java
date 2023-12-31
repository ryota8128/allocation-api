package com.example.moneyAllocation.service.impl;

import com.example.moneyAllocation.domain.TemplateTransferList;
import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.domain.service.TemporaryDomainService;
import com.example.moneyAllocation.repository.TemplateTransferRepository;
import com.example.moneyAllocation.repository.TemporaryTransferRepository;
import com.example.moneyAllocation.repository.TransferRepository;
import com.example.moneyAllocation.service.TransferService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {
  private final TransferRepository transferRepository;
  private final TemplateTransferRepository templateTransferRepository;
  private final TemporaryDomainService temporaryDomainService;
  private final TemporaryTransferRepository temporaryTransferRepository;

  @Override
  public List<Transfer> find(Long userId) {
    return transferRepository.find(userId);
  }

  @Override
  public Transfer findOne(TransferSelector selector) {
    return transferRepository.findOne(selector);
  }

  @Override
  @Transactional
  public Transfer add(Transfer transfer) {
    Long transferId = transferRepository.add(transfer);
    log.info("追加したTransfer id: {}", transferId);
    TemplateTransferList templateTransferList =
        templateTransferRepository.find(transfer.getUserId());
    log.info("取得したtemplate: {}件", templateTransferList.getList().size());
    temporaryDomainService.insertTemplateList(templateTransferList, transferId);
    log.info("template listの追加完了");
    return transferRepository.findOne(TransferSelector.withId(transferId, transfer.getUserId()));
  }

  @Override
  public void set(Transfer transfer) {
    transferRepository.set(transfer);
  }

  @Override
  @Transactional
  public void delete(TransferSelector transferSelector) {
    // 関連するTemporaryTransferを削除する
    TransferSelector temporarySelector =
            TransferSelector.withTransferId(transferSelector.getId(), transferSelector.getUserId());
    temporaryTransferRepository.delete(temporarySelector);

    transferRepository.delete(transferSelector);
  }
}
