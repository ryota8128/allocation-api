package com.example.moneyAllocation.repository.impl;

import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.TemporaryTransferRepository;
import com.example.moneyAllocation.repository.TransferRepository;
import com.example.moneyAllocation.repository.mybatis.TransferMapper;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class TransferRepositoryImpl implements TransferRepository {
  private final SqlSession sqlSession;

  private final TemporaryTransferRepository temporaryTransferRepository;

  public TransferRepositoryImpl(
      SqlSession sqlSession, TemporaryTransferRepository temporaryTransferRepository) {
    this.sqlSession = sqlSession;
    this.temporaryTransferRepository = temporaryTransferRepository;
  }

  @Override
  public List<Transfer> find(Long userId) {
    return sqlSession.getMapper(TransferMapper.class).find(userId);
  }

  @Override
  public Transfer findOne(TransferSelector selector) {
    Transfer transfer = sqlSession.getMapper(TransferMapper.class).findOne(selector);
    if (transfer == null) {
      throw new ResourceNotFoundException("Transfer not found");
    }
    return transfer;
  }

  @Override
  public Long add(Transfer transfer) {
    int affected = sqlSession.getMapper(TransferMapper.class).add(transfer);
    if (affected != 1) {
      throw new RuntimeException("データの追加に失敗しました");
    }
    return transfer.getId();
  }

  @Override
  public void set(Transfer transfer) {
    int affected = sqlSession.getMapper(TransferMapper.class).set(transfer);
    if (affected != 1) {
      throw new ResourceNotFoundException("Transfer not found");
    }
  }

  @Override
  public void delete(TransferSelector transferSelector) {
    // 関連するTemporaryTransferを削除する
    TemporaryTransferSelector temporarySelector = new TemporaryTransferSelector();
    temporarySelector.setTransferId(transferSelector.getId());
    temporaryTransferRepository.delete(temporarySelector);

    // 指定されたTransferの削除
    int affected = sqlSession.getMapper(TransferMapper.class).delete(transferSelector);
    if (affected != 1) {
      throw new ResourceNotFoundException("Transfer not found");
    }
  }
}
