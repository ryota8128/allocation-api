package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.moneyAllocation.MoneyAllocationApplication;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.util.DbTestExecutionListener;
import com.example.moneyAllocation.util.TestDomainDataCreator;
import com.example.moneyAllocation.repository.util.DbUnitUtil;
import java.io.File;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class RegularTransferRepositoryDbUnitTest {
    private final String DATA_DIR = this.getClass().getResource("").getFile() + "../data" + File.separator;

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class FindDbTest {
        @Autowired
        private RegularTransferRepository repository;
        @Autowired
        private DataSource source;

        @Test
        public void testFindWithUserId() {
            Long userId = 1L;
            List<RegularTransfer> regularTransferList = repository.find(userId);
            assertEquals(2, regularTransferList.size());
            assertEquals(1L, regularTransferList.get(0).getId());
            assertEquals(1L, regularTransferList.get(0).getFromAccount());
            assertEquals(2L, regularTransferList.get(0).getToAccount());
            assertEquals("三井", regularTransferList.get(0).getFromAccountName());
            assertEquals("楽天", regularTransferList.get(0).getToAccountName());
            assertEquals("desc1", regularTransferList.get(0).getDescription());
            assertEquals(false, regularTransferList.get(0).getPercentage());
            assertEquals(30000, regularTransferList.get(0).getAmount());
            assertNull(regularTransferList.get(0).getRatio());
            assertEquals(1L, regularTransferList.get(0).getUserId());

            assertEquals(2L, regularTransferList.get(1).getId());
            assertEquals(true, regularTransferList.get(1).getPercentage());
            assertNull(regularTransferList.get(1).getAmount());
            assertEquals((float) 0.4, regularTransferList.get(1).getRatio());
        }

        @Test
        public void testFindOne() {
            TransferSelector selector = new TransferSelector();
            selector.setId(1L);
            selector.setUserId(1L);
            RegularTransfer regularTransfer = repository.findOne(selector);
            assertEquals(1L, regularTransfer.getId());
            assertEquals(1L, regularTransfer.getFromAccount());
            assertEquals(2L, regularTransfer.getToAccount());
            assertEquals("三井", regularTransfer.getFromAccountName());
            assertEquals("楽天", regularTransfer.getToAccountName());
            assertEquals("desc1", regularTransfer.getDescription());
            assertEquals(false, regularTransfer.getPercentage());
            assertEquals(30000, regularTransfer.getAmount());
            assertNull(regularTransfer.getRatio());
        }

        @Test
        public void testFindOneNotExist() {
            TransferSelector selector = new TransferSelector();
            selector.setId(1L);
            selector.setUserId(2L);
            assertThrows(ResourceNotFoundException.class, () -> repository.findOne(selector));
        }
    }

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class InsertDbTest {
        @Autowired
        private RegularTransferRepository repository;
        @Autowired
        private DataSource source;

        private final File insertExpectedData = new File(DATA_DIR + "regular_insert_expected.xlsx");

        @Test
        public void testInsert() {
            RegularTransfer regularTransfer =
                    TestDomainDataCreator.regularCreate(4L, 3L, 1L, false, 10000, null, "desc4", 1L);
            repository.add(regularTransfer);
            DbUnitUtil.assertMutateResult(source, "REGULAR_TRANSFER", insertExpectedData, List.of());
        }
    }

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class UpdateDbTest {
        @Autowired
        private RegularTransferRepository repository;
        @Autowired
        private DataSource source;

        private final File updateExpectedData = new File(DATA_DIR + "regular_update_expected.xlsx");
        private final File setNullExpectedData = new File(DATA_DIR + "regular_setNull_expected.xlsx");

        @Test
        public void testUpdate() {
            RegularTransfer regularTransfer =
                    TestDomainDataCreator.regularCreate(3L, 4L, 5L, Boolean.TRUE, null, (float) 0.125, "sample", 2L);

            repository.set(regularTransfer);
            DbUnitUtil.assertMutateResult(source, "REGULAR_TRANSFER", updateExpectedData, List.of());
        }

        @Test
        public void testSetNull() {
            repository.setNullAccount(2L);
            DbUnitUtil.assertMutateResult(source, "REGULAR_TRANSFER", setNullExpectedData, List.of());
        }


    }

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class DeleteDbTest {
        @Autowired
        private RegularTransferRepository repository;
        @Autowired
        private DataSource source;

        private final File deleteExpectedData = new File(DATA_DIR + "regular_delete_expected.xlsx");

        @Test
        public void testDelete() {
            TransferSelector selector = new TransferSelector();
            selector.setId(3L);
            selector.setUserId(2L);
            repository.delete(selector);
            DbUnitUtil.assertMutateResult(source, "REGULAR_TRANSFER", deleteExpectedData, List.of());
        }

        @Test
        public void testFailDelete() {
            TransferSelector selector = new TransferSelector();
            selector.setId(3L);
            selector.setUserId(1L);
            assertThrows(ResourceNotFoundException.class, () -> repository.delete(selector));
        }
    }

}
