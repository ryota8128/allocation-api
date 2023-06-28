package com.example.moneyAllocation.repository;

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
        public void testFind() {
            AccountSelector selector = new AccountSelector();
            List<Account> accountList = repository.find(selector);
            System.out.println(accountList);
        }


    }
}
