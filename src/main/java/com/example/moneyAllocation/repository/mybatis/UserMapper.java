package com.example.moneyAllocation.repository.mybatis;

import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.domain.UserSelector;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User find(UserSelector selector);

    int add(User user);

    int update(User user);

    int delete(@Param("id") Long id);
}
