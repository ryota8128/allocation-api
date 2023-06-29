package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.moneyAllocation.MoneyAllocationApplication;
import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.repository.util.DbTestExecutionListener;
import java.io.File;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class AccountRepositoryImplDbUnitTest {
    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class FindDbTest {
        @Autowired
        private AccountRepository repository;
        @Autowired
        private DataSource source;


        private final String DATA_DIR = this.getClass().getResource("").getFile() + "data" + File.separator;

        @Test
        public void testFindAll() {
            AccountSelector selector = new AccountSelector();
            List<Account> accountList = repository.find(selector);
            assertEquals(accountList.get(0).getId(), 1L);
            assertEquals(accountList.get(0).getName(), "三井");
            assertEquals(accountList.get(0).getNumFreeTransfer(), 9999);
            assertEquals(accountList.get(0).getTransferFee(), 150);
            assertEquals(accountList.get(0).getOwnerId(), 1L);
            assertEquals(accountList.get(1).getId(), 2L);
            assertEquals(accountList.get(1).getName(), "楽天");
            assertEquals(accountList.get(1).getNumFreeTransfer(), 2);
            assertEquals(accountList.get(1).getTransferFee(), 130);
            assertEquals(accountList.get(1).getOwnerId(), 1L);
            assertEquals(accountList.get(2).getId(), 3L);
            assertEquals(accountList.get(2).getName(), "住信SBI");
            assertEquals(accountList.get(2).getNumFreeTransfer(), 5);
            assertEquals(accountList.get(2).getTransferFee(), 100);
            assertEquals(accountList.get(2).getOwnerId(), 1L);
            assertEquals(accountList.get(3).getId(), 4L);
            assertEquals(accountList.get(3).getName(), "PayPay");
            assertEquals(accountList.get(3).getNumFreeTransfer(), 3);
            assertEquals(accountList.get(3).getTransferFee(), 140);
            assertEquals(accountList.get(3).getOwnerId(), 2L);
        }

        @Test
        public void testFindWithOwnerId() {
            AccountSelector selector = new AccountSelector();
            selector.setOwnerId(2L);
            List<Account> accountList = repository.find(selector);
            assertEquals(accountList.get(0).getId(), 4L);
            assertEquals(accountList.get(0).getName(), "PayPay");
            assertEquals(accountList.get(0).getNumFreeTransfer(), 3);
            assertEquals(accountList.get(0).getTransferFee(), 140);
            assertEquals(accountList.get(0).getOwnerId(), 2L);
        }
    }
}
