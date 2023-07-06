package com.example.moneyAllocation.repository.mybatis;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TemporaryTransferMapper {
    List<TemporaryTransfer> find(TemporaryTransferSelector selector);

    TemporaryTransfer findOne(@Param("id") Long id);

    int add(TemporaryTransfer temporaryTransfer);

    int set(TemporaryTransfer temporaryTransfer);

    int delete(Long id);
}

