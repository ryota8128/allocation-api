package com.example.moneyAllocation.domain;

import com.example.moneyAllocation.domain.dto.TemplateTransferDto;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class TemplateTransfer implements HaveToAndFromAccount {
  private final Long id;
  private final Long fromAccount;
  private final Long toAccount;
  private final String fromAccountName;
  private final String toAccountName;
  private final String description;
  private final Long userId;

  private static final Pattern NON_WHITESPACE = Pattern.compile("[^\\s\\u3000]");

  @Override
  public List<AccountSelector> fetchSelectorList() {
    return Stream.of(fromAccount, toAccount)
        .filter(Objects::nonNull)
        .map(a -> AccountSelector.of(a, userId))
        .collect(Collectors.toList());
  }

  public boolean isValidDescription() {
    if (description == null) return false;
    return NON_WHITESPACE.matcher(description).find();
  }

  public static TemplateTransfer from(TemplateTransferDto templateTransferDto) {
    return TemplateTransfer.builder()
        .id(templateTransferDto.getId())
        .fromAccount(templateTransferDto.getFromAccount())
        .toAccount(templateTransferDto.getToAccount())
        .fromAccountName(templateTransferDto.getFromAccountName())
        .toAccountName(templateTransferDto.getToAccountName())
        .description(templateTransferDto.getDescription())
        .userId(templateTransferDto.getUserId())
        .build();
  }
}
