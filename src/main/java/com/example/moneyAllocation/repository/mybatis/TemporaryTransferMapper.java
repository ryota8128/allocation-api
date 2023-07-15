package com.example.moneyAllocation.repository.mybatis;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TemporaryTransferMapper {
    List<TemporaryTransfer> find(Long userId);

    TemporaryTransfer findOne(TemporaryTransferSelector selector);

    int add(TemporaryTransfer temporaryTransfer);

    int set(TemporaryTransfer temporaryTransfer);

    int delete(TemporaryTransferSelector selector);

    void setNullAccount(Long accountId);
}

