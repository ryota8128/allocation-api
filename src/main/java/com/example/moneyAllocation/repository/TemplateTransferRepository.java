package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.domain.TemplateTransferList;
import com.example.moneyAllocation.domain.dto.TemplateTransferDto;
import com.example.moneyAllocation.repository.mybatis.TemplateTransferMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TemplateTransferRepository implements ITemplateTransferRepository {
  private final SqlSession sqlSession;

  @Override
  public TemplateTransferList find(Long userId) {
    List<TemplateTransferDto> templateDtoList =
        sqlSession.getMapper(TemplateTransferMapper.class).find(userId);

    return TemplateTransferList.fromDtoList(templateDtoList);
  }

  @Override
  public TemplateTransfer findOne(Long id, Long userId) {
    return TemplateTransfer.fromDto(
        sqlSession.getMapper(TemplateTransferMapper.class).findOne(id, userId));
  }
}
