package com.example.moneyAllocation.repository.mybatis;

import com.example.moneyAllocation.domain.Account;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {
    @Select("select * from account")
    List<Account> find();
}
