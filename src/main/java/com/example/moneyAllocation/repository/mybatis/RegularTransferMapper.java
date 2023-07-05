package com.example.moneyAllocation.repository.mybatis;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegularTransferMapper {
    List<RegularTransfer> find(RegularTransferSelector selector);

    RegularTransfer findOne(@Param("id") Long id);

    int add(RegularTransfer regularTransfer);

    int set(RegularTransfer regularTransfer);

    int delete(@Param("id") Long id);
}
