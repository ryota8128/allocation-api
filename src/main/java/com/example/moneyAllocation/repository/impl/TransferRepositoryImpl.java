package com.example.moneyAllocation.repository.impl;

import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.TransferRepository;
import com.example.moneyAllocation.repository.mybatis.TransferMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransferRepositoryImpl implements TransferRepository {
  private final SqlSession sqlSession;

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
    int affected = sqlSession.getMapper(TransferMapper.class).delete(transferSelector);
    if (affected != 1) {
      throw new ResourceNotFoundException("Transfer not found");
    }
  }
}
