package com.example.moneyAllocation.repository.impl;

import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.domain.TemplateTransferList;
import com.example.moneyAllocation.domain.dto.TemplateTransferDto;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.TemplateTransferRepository;
import com.example.moneyAllocation.repository.mybatis.TemplateTransferMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TemplateTransferRepositoryImpl implements TemplateTransferRepository {
  private final SqlSession sqlSession;

  @Override
  public TemplateTransferList find(Long userId) {
    List<TemplateTransferDto> templateDtoList =
        sqlSession.getMapper(TemplateTransferMapper.class).find(userId);

    return TemplateTransferList.fromDtoList(templateDtoList);
  }

  @Override
  public TemplateTransfer findOne(Long id, Long userId) {
    TemplateTransferDto findDto =
        sqlSession.getMapper(TemplateTransferMapper.class).findOne(id, userId);
    if (findDto == null) {
      throw new ResourceNotFoundException("見つかりません");
    }
    return TemplateTransfer.fromDto(findDto);
  }
}
