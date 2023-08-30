package com.example.moneyAllocation.domain.dto;

import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.domain.TemplateTransferList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TemplateTransferDto {
  private Long id;
  private Long fromAccount;
  private Long toAccount;
  private String fromAccountName;
  private String toAccountName;
  private String description;
  private Long userId;

  public static List<TemplateTransferDto> valueFrom(TemplateTransferList list) {
    return list.getList().stream().map(TemplateTransferDto::valueOf).collect(Collectors.toList());
  }

  public static TemplateTransferDto valueOf(TemplateTransfer templateTransfer) {
    return builder()
        .id(templateTransfer.getId())
        .fromAccount(templateTransfer.getFromAccount())
        .toAccount(templateTransfer.getToAccount())
        .fromAccountName(templateTransfer.getFromAccountName())
        .toAccountName(templateTransfer.getToAccountName())
        .description(templateTransfer.getDescription())
        .userId(templateTransfer.getUserId())
        .build();
  }
}
