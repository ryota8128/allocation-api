package com.example.moneyAllocation.repository.mybatis;


import com.example.moneyAllocation.domain.Transfer;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransferMapper {
    List<Transfer> find(Long userId);

    Transfer findOne(Long transferId);

    int add(Transfer transfer);

    int set(Transfer transfer);

    int delete(Long transferId);

}
