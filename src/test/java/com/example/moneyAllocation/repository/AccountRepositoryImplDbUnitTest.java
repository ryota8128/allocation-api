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
            assertEquals(5, accountList.size());
            assertEquals(1L, accountList.get(0).getId());
            assertEquals("三井", accountList.get(0).getName());
            assertEquals(9999, accountList.get(0).getNumFreeTransfer());
            assertEquals(150, accountList.get(0).getTransferFee());
            assertEquals(1L, accountList.get(0).getOwnerId());
            assertEquals(4L, accountList.get(3).getId());
            assertEquals("PayPay", accountList.get(3).getName());
            assertEquals(3, accountList.get(3).getNumFreeTransfer());
            assertEquals(140, accountList.get(3).getTransferFee());
            assertEquals(2L, accountList.get(3).getOwnerId());
            assertEquals(5L, accountList.get(4).getId());
            assertEquals("ソニー", accountList.get(4).getName());
            assertEquals(2, accountList.get(4).getNumFreeTransfer());
            assertEquals(120, accountList.get(4).getTransferFee());
            assertEquals(2L, accountList.get(4).getOwnerId());

        }

        @Test
        public void testFindWithOwnerId() {
            AccountSelector selector = new AccountSelector();
            selector.setOwnerId(2L);
            List<Account> accountList = repository.find(selector);
            assertEquals(2, accountList.size());
            assertEquals(4L, accountList.get(0).getId());
            assertEquals("PayPay", accountList.get(0).getName());
            assertEquals(3, accountList.get(0).getNumFreeTransfer());
            assertEquals(140, accountList.get(0).getTransferFee());
            assertEquals(2L, accountList.get(0).getOwnerId());
            assertEquals(5L, accountList.get(1).getId());
            assertEquals("ソニー", accountList.get(1).getName());
            assertEquals(2, accountList.get(1).getNumFreeTransfer());
            assertEquals(120, accountList.get(1).getTransferFee());
            assertEquals(2L, accountList.get(1).getOwnerId());
        }

        @Test
        public void testFindOne() {
            Account account = repository.findOne(1L);
            assertEquals(1L, account.getId());
            assertEquals("三井", account.getName());
            assertEquals(9999, account.getNumFreeTransfer());
            assertEquals(150, account.getTransferFee());
            assertEquals(1L, account.getOwnerId());
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
