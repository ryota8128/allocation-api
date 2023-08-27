package com.example.moneyAllocation.domain.dto;


import lombok.Data;

@Data
public class TemplateTransferDto {
  private Long Id;
  private Long fromAccount;
  private Long toAccount;
  private String description;
  private Long userId;
}
