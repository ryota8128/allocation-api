package com.example.moneyAllocation.domain;

import com.example.moneyAllocation.domain.dto.TemplateTransferDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
public class TemplateTransferList {
  @Getter private final List<TemplateTransfer> list;

  public static TemplateTransferList fromDtoList(List<TemplateTransferDto> dtoList) {
    return of(dtoList.stream().map(TemplateTransfer::fromDto).collect(Collectors.toList()));
  }

  public static TemplateTransferList of(List<TemplateTransfer> templateTransfers) {
    return builder().list(templateTransfers).build();
  }
}
