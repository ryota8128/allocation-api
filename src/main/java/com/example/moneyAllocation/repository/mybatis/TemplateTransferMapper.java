package com.example.moneyAllocation.repository.mybatis;

import com.example.moneyAllocation.domain.dto.TemplateTransferDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TemplateTransferMapper {
  List<TemplateTransferDto> find(Long userId);

  TemplateTransferDto findOne(@Param("id") Long id, @Param("userId") Long userId);

  int insert(TemplateTransferDto dto);
}
