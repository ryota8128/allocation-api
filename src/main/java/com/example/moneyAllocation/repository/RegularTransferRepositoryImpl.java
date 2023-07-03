package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
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

    }

    @Override
    public void set(RegularTransfer regularTransfer) {

    }

    @Override
    public void delete(Long id) {

    }
}
