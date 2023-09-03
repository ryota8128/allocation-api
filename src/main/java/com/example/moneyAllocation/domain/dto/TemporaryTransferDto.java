package com.example.moneyAllocation.domain.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemporaryTransferDto {
    private Long id;
    private Long fromAccount;
    private Long toAccount;
    private String fromAccountName;
    private String toAccountName;
    private String description;
    private Integer amount;
    private Long userId;
    private Long transferId;
}
