package com.example.moneyAllocation.repository.mybatis;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegularTransferMapper {
    List<RegularTransfer> find(Long userId);

    RegularTransfer findOne(TransferSelector selector);

    int add(RegularTransfer regularTransfer);

    int set(RegularTransfer regularTransfer);

    int delete(TransferSelector selector);

    void setNullAccount(Long accountId);
}
