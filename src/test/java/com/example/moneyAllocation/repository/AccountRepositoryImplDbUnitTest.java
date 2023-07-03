package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import com.example.moneyAllocation.MoneyAllocationApplication;
import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.repository.util.DbTestExecutionListener;
import com.example.moneyAllocation.util.DbUnitUtil;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class AccountRepositoryImplDbUnitTest {

    private final String DATA_DIR = this.getClass().getResource("").getFile() + "data" + File.separator;

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class FindDbTest {
        @Autowired
        private AccountRepository repository;
        @Autowired
        private DataSource source;

        @Test
        public void testFindAll() {
            AccountSelector selector = new AccountSelector();
            List<Account> accountList = repository.find(selector);
            assertEquals(accountList.size(), 5);
            assertEquals(accountList.get(0).getId(), 1L);
            assertEquals(accountList.get(0).getName(), "三井");
            assertEquals(accountList.get(0).getNumFreeTransfer(), 9999);
            assertEquals(accountList.get(0).getTransferFee(), 150);
            assertEquals(accountList.get(0).getOwnerId(), 1L);
            assertEquals(accountList.get(3).getId(), 4L);
            assertEquals(accountList.get(3).getName(), "PayPay");
            assertEquals(accountList.get(3).getNumFreeTransfer(), 3);
            assertEquals(accountList.get(3).getTransferFee(), 140);
            assertEquals(accountList.get(3).getOwnerId(), 2L);
            assertEquals(accountList.get(4).getId(), 5L);
            assertEquals(accountList.get(4).getName(), "ソニー");
            assertEquals(accountList.get(4).getNumFreeTransfer(), 2);
            assertEquals(accountList.get(4).getTransferFee(), 120);
            assertEquals(accountList.get(4).getOwnerId(), 2L);
        }

        @Test
        public void testFindWithOwnerId() {
            AccountSelector selector = new AccountSelector();
            selector.setOwnerId(2L);
            List<Account> accountList = repository.find(selector);
            assertEquals(accountList.size(), 2);
            assertEquals(accountList.get(0).getId(), 4L);
            assertEquals(accountList.get(0).getName(), "PayPay");
            assertEquals(accountList.get(0).getNumFreeTransfer(), 3);
            assertEquals(accountList.get(0).getTransferFee(), 140);
            assertEquals(accountList.get(0).getOwnerId(), 2L);
            assertEquals(accountList.get(1).getId(), 5L);
            assertEquals(accountList.get(1).getName(), "ソニー");
            assertEquals(accountList.get(1).getNumFreeTransfer(), 2);
            assertEquals(accountList.get(1).getTransferFee(), 120);
            assertEquals(accountList.get(1).getOwnerId(), 2L);
        }
    }

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class AddDbTest {
        @Autowired
        private AccountRepository repository;
        @Autowired
        private DataSource source;

        private final File expectedData = new File(DATA_DIR + "account_insert_expected.xlsx");
        @Test
        public void testInsertWhenSuccess() {
            Account account = new Account();
            account.setName("sony");
            account.setNumFreeTransfer(2);
            account.setTransferFee(130);
            account.setOwnerId(2L);

            repository.add(account);
            DbUnitUtil.assertMutateResult(source, "ACCOUNT", expectedData, Arrays.asList("ID"));
        }

        @Test
        public void testInsertConstraintException() {
            Account account = new Account();
            account.setName("hoge");
            account.setTransferFee(120);
            account.setNumFreeTransfer(4);
            account.setOwnerId(10L);

            assertThrowsExactly(DataIntegrityViolationException.class, () -> repository.add(account));
        }
    }

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class UpdateDbTest {
        @Autowired
        private AccountRepository repository;
        @Autowired
        private DataSource source;

        private final File expectedData = new File(DATA_DIR + "account_update_expected.xlsx");

        @Test
        public void testUpdateSuccess() {
            Account account = new Account();
            account.setId(4L);
            account.setName("PayPay");
            account.setNumFreeTransfer(2);
            account.setTransferFee(140);
            account.setOwnerId(2L);

            repository.set(account);
            DbUnitUtil.assertMutateResult(source, "ACCOUNT", expectedData, Arrays.asList());
        }

        @Test
        public void testUpdateNotExistId() {
            Account account = new Account();
            account.setId(99L);
            account.setName("PayPay");
            account.setNumFreeTransfer(2);
            account.setTransferFee(140);
            account.setOwnerId(2L);

            assertThrows(ResourceNotFoundException.class, () -> repository.set(account));
        }
    }

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class DeleteDbTest {
        @Autowired
        private AccountRepository repository;
        @Autowired
        private DataSource source;

        private final File expectedData = new File(DATA_DIR + "account_delete_expected.xlsx");

        // TODO: account削除前にregular, temporaryを削除する処理を追加してから実装
//        @Test
//        public void testDeleteSuccess() {
//            Long accountId = 2L;
//            repository.delete(accountId);
//            DbUnitUtil.assertMutateResult(source, "ACCOUNT", expectedData, Arrays.asList());
//        }

        @Test
        public void testDeleteNotExistsId() {
            Long accountId = 8L;
            assertThrows(ResourceNotFoundException.class, () -> repository.delete(accountId));
        }
    }
}
