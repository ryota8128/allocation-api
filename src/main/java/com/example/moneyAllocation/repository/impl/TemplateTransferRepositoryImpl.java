package com.example.moneyAllocation.repository.impl;

import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.domain.TemplateTransferList;
import com.example.moneyAllocation.domain.dto.TemplateTransferDto;
import com.example.moneyAllocation.exception.BudRequestException;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.TemplateTransferRepository;
import com.example.moneyAllocation.repository.mybatis.TemplateTransferMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
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
      log.error("template取得失敗");
      throw new ResourceNotFoundException("見つかりません");
    }
    return TemplateTransfer.from(findDto);
  }

  @Override
  public Long insert(TemplateTransfer templateTransfer) {
    TemplateTransferDto dto = TemplateTransferDto.valueOf(templateTransfer);
    int affected = sqlSession.getMapper(TemplateTransferMapper.class).insert(dto);
    if (affected != 1) {
      log.error("template追加失敗");
      throw new BudRequestException("データの追加に失敗しました．");
    }
    return dto.getId();
  }

  @Override
  public void update(TemplateTransfer templateTransfer) {
    TemplateTransferDto dto = TemplateTransferDto.valueOf(templateTransfer);
    int affected = sqlSession.getMapper(TemplateTransferMapper.class).set(dto);
    if (affected != 1) {
      log.error("template更新失敗");
      throw new BudRequestException("データの更新に失敗しました");
    }
  }

  @Override
  public void delete(Long id, Long userId) {
    int affected = sqlSession.getMapper(TemplateTransferMapper.class).delete(id, userId);
    if (affected != 1) {
      log.error("template削除失敗");
      throw new ResourceNotFoundException("404");
    }
  }
}
