package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.repository.mybatis.TransferMapper;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class TransferRepositoryImpl implements TransferRepository {
    private final SqlSession sqlSession;

    public TransferRepositoryImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<Transfer> find(Long userId) {
        return sqlSession.getMapper(TransferMapper.class).find(userId);
    }

    @Override
    public Transfer findOne(TransferSelector selector) {
        return sqlSession.getMapper(TransferMapper.class).findOne(selector);
    }

    @Override
    public void add(Transfer transfer) {

    }

    @Override
    public void set(Transfer transfer) {

    }

    @Override
    public void delete(TransferSelector selector) {

    }
}
