package com.example.moneyAllocation.repository.mybatis;


import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransferMapper {
    List<Transfer> find(Long userId);

    Transfer findOne(TransferSelector selector);

    int add(Transfer transfer);

    int set(Transfer transfer);

    int delete(TransferSelector selector);

}
