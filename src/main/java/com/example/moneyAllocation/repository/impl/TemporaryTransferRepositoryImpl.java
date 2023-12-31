package com.example.moneyAllocation.repository.impl;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.TemporaryTransferRepository;
import com.example.moneyAllocation.repository.mybatis.TemporaryTransferMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class TemporaryTransferRepositoryImpl implements TemporaryTransferRepository {

  private final SqlSession sqlSession;

  public TemporaryTransferRepositoryImpl(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public List<TemporaryTransfer> find(TransferSelector selector) {
    return sqlSession.getMapper(TemporaryTransferMapper.class).find(selector);
  }

  @Override
  public TemporaryTransfer findOne(TransferSelector selector) {
    TemporaryTransfer temporaryTransfer =
        sqlSession.getMapper(TemporaryTransferMapper.class).findOne(selector);
    if (temporaryTransfer == null) {
      throw new ResourceNotFoundException("TemporaryTransfer not found");
    }
    return temporaryTransfer;
  }

  @Override
  public Long add(TemporaryTransfer temporaryTransfer) {
    int affected = sqlSession.getMapper(TemporaryTransferMapper.class).add(temporaryTransfer);
    if (affected != 1) {
      throw new RuntimeException("データの追加に失敗しました．");
    }
    log.info("追加したTemporaryTransfer id: {}", temporaryTransfer.getId());
    return temporaryTransfer.getId();
  }

  @Override
  public void set(TemporaryTransfer temporaryTransfer) {
    int affected = sqlSession.getMapper(TemporaryTransferMapper.class).set(temporaryTransfer);
    if (affected != 1) {
      throw new ResourceNotFoundException("TemporaryTransfer not found");
    }
  }

  @Override
  public void delete(TransferSelector selector) {
    int affected = sqlSession.getMapper(TemporaryTransferMapper.class).delete(selector);
    // userIdとid指定で削除するときは1つだけ削除されないと例外をスロー
    if (selector.getTransferId() == null && affected != 1) {
      throw new ResourceNotFoundException("TemporaryTransfer not found");
    }
  }

  @Override
  public void setNullAccount(Long accountId) {
    this.sqlSession.getMapper(TemporaryTransferMapper.class).setNullAccount(accountId);
  }
}
