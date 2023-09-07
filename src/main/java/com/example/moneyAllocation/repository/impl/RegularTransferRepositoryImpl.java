package com.example.moneyAllocation.repository.impl;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.RegularTransferRepository;
import com.example.moneyAllocation.repository.mybatis.RegularTransferMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class RegularTransferRepositoryImpl implements RegularTransferRepository {
  private final SqlSession sqlSession;

  public RegularTransferRepositoryImpl(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public List<RegularTransfer> find(Long userId) {
    return this.sqlSession.getMapper(RegularTransferMapper.class).find(userId);
  }

  @Override
  public RegularTransfer findOne(TransferSelector selector) {
    RegularTransfer regularTransfer =
        this.sqlSession.getMapper(RegularTransferMapper.class).findOne(selector);
    if (regularTransfer == null) {
      throw new ResourceNotFoundException("RegularTransfer not found");
    }
    return regularTransfer;
  }

  @Override
  public Long add(RegularTransfer regularTransfer) {
    int affected = this.sqlSession.getMapper(RegularTransferMapper.class).add(regularTransfer);
    if (affected != 1) {
      throw new RuntimeException("データの追加に失敗しました．");
    }
    log.info("追加したRegularTransfer id: {}", regularTransfer.getId());
    return regularTransfer.getId();
  }

  @Override
  public void set(RegularTransfer regularTransfer) {
    int affected = this.sqlSession.getMapper(RegularTransferMapper.class).set(regularTransfer);
    if (affected != 1) {
      throw new ResourceNotFoundException("RegularTransfer not found");
    }
  }

  @Override
  public void delete(TransferSelector selector) {
    int affected = this.sqlSession.getMapper(RegularTransferMapper.class).delete(selector);
    if (affected != 1) {
      throw new ResourceNotFoundException("RegularTransfer not found");
    }
  }

  @Override
  public void setNullAccount(Long accountId) {
    this.sqlSession.getMapper(RegularTransferMapper.class).setNullAccount(accountId);
  }
}
