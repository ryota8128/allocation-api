package com.example.moneyAllocation.repository.mybatis;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegularTransferMapper {
    List<RegularTransfer> find(RegularTransferSelector selector);

    RegularTransfer findOne(RegularTransferSelector selector);

    int add(RegularTransfer regularTransfer);

    int set(RegularTransfer regularTransfer);

    int delete(RegularTransferSelector selector);
}
