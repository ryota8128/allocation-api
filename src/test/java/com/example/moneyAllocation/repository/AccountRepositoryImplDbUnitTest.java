package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import com.example.moneyAllocation.MoneyAllocationApplication;
import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.util.DbTestExecutionListener;
import com.example.moneyAllocation.repository.util.DbUnitUtil;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class AccountRepositoryImplDbUnitTest {

    private final String DATA_DIR = this.getClass().getResource("").getFile() + "../data" + File.separator;

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class FindDbTest {
        @Autowired
        private AccountRepository repository;
        @Autowired
        private DataSource source;

        @Test
        public void testFindWithOwnerId() {
            Long ownerId = 2L;
            List<Account> accountList = repository.find(ownerId);
            assertEquals(3, accountList.size());
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
            assertEquals(6L, accountList.get(2).getId());
            assertEquals("ゆうちょ", accountList.get(2).getName());
            assertEquals(0, accountList.get(2).getNumFreeTransfer());
            assertEquals(130, accountList.get(2).getTransferFee());
            assertEquals(2L, accountList.get(2).getOwnerId());
        }

        @Test
        public void testFindOneWithId() {
            AccountSelector selector = new AccountSelector();
            selector.setOwnerId(1L);
            selector.setId(1L);
            Account account = repository.findOne(selector);
            assertEquals(1L, account.getId());
            assertEquals("三井", account.getName());
            assertEquals(9999, account.getNumFreeTransfer());
            assertEquals(150, account.getTransferFee());
            assertEquals(1L, account.getOwnerId());
        }

        @Test
        public void testFindOneWithName() {
            AccountSelector selector = new AccountSelector();
            selector.setOwnerId(1L);
            selector.setName("三井");
            Account account = repository.findOne(selector);
            assertEquals(1L, account.getId());
            assertEquals("三井", account.getName());
            assertEquals(9999, account.getNumFreeTransfer());
            assertEquals(150, account.getTransferFee());
            assertEquals(1L, account.getOwnerId());
        }

        @Test
        public void testFindOneWithNameAndId() {
            AccountSelector selector = new AccountSelector();
            selector.setOwnerId(1L);
            selector.setName("三井");
            selector.setId(1L);
            Account account = repository.findOne(selector);
            assertEquals(1L, account.getId());
            assertEquals("三井", account.getName());
            assertEquals(9999, account.getNumFreeTransfer());
            assertEquals(150, account.getTransferFee());
            assertEquals(1L, account.getOwnerId());
        }

        @Test
        public void testFindOneWithInvalidNameAndId() {
            AccountSelector selector = new AccountSelector();
            selector.setOwnerId(1L);
            selector.setName("三井");
            selector.setId(2L);
            assertThrows(ResourceNotFoundException.class, () -> repository.findOne(selector));
        }


        @Test
        public void testFindOneWhenNotExists() {
            AccountSelector selector = new AccountSelector();
            selector.setOwnerId(2L);
            selector.setId(1L);
            assertThrows(ResourceNotFoundException.class, () -> repository.findOne(selector));
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
        public void testInsertOwnerIdConstraintException() {
            Account account = new Account();
            account.setName("hoge");
            account.setTransferFee(120);
            account.setNumFreeTransfer(4);
            account.setOwnerId(10L);

            assertThrowsExactly(DataIntegrityViolationException.class, () -> repository.add(account));
        }

        @Test
        public void testInsertUniqueNameAndOwnerIdConstraintException() {
            Account account = new Account();
            account.setName("三井");
            account.setTransferFee(120);
            account.setNumFreeTransfer(4);
            account.setOwnerId(1L);

            assertThrowsExactly(DuplicateKeyException.class, () -> repository.add(account));
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
        @Test
        public void testDeleteWithSetNullAccount() {
            AccountSelector selector = new AccountSelector();
            selector.setId(2L);
            selector.setOwnerId(1L);
//            repository.delete(selector);
//            DbUnitUtil.assertMutateResult(source, "ACCOUNT", expectedData, Arrays.asList());
//            DbUnitUtil.assertMutateResult(source, "REGULAR_TRANSFER", expectedData, Arrays.asList());
//            DbUnitUtil.assertMutateResult(source, "TEMPORARY_TRANSFER", expectedData, Arrays.asList());
        }

        @Test
        public void testDeleteNotExistsId() {
            AccountSelector selector = new AccountSelector();
            selector.setId(6L);
            selector.setOwnerId(1L);
            assertThrows(ResourceNotFoundException.class, () -> repository.delete(selector));
        }
    }
}
