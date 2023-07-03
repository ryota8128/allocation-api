package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.repository.mybatis.AccountMapper;
import com.example.moneyAllocation.repository.mybatis.RegularTransferMapper;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class RegularTransferRepositoryImpl implements RegularTransferRepository{
    private final SqlSession sqlSession;

    public RegularTransferRepositoryImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<RegularTransfer> find(RegularTransferSelector selector) {
        return this.sqlSession.getMapper(RegularTransferMapper.class).find(selector);
    }

    @Override
    public void add(RegularTransfer regularTransfer) {
        int affected = this.sqlSession.getMapper(RegularTransferMapper.class).add(regularTransfer);
        if (affected != 1) {
            throw new RuntimeException("データの追加に失敗しました．");
        }

    }

    @Override
    public void set(RegularTransfer regularTransfer) {

    }

    @Override
    public void delete(Long id) {

    }
}
