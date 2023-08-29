package com.example.moneyAllocation.domain;

import java.util.List;

public interface HaveToAndFromAccount {
  Long getToAccount();

  Long getFromAccount();

  List<AccountSelector> getSelectorList();
}
