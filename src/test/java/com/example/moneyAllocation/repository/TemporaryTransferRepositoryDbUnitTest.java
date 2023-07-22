package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.moneyAllocation.MoneyAllocationApplication;
import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.util.DbTestExecutionListener;
import com.example.moneyAllocation.repository.util.TestDomainDataCreator;
import com.example.moneyAllocation.util.DbUnitUtil;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class TemporaryTransferRepositoryDbUnitTest {
    private final String DATA_DIR = this.getClass().getResource("")
            .getFile() + "data" + File.separator;

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class FindDbTest {
        @Autowired
        private TemporaryTransferRepository repository;
        @Autowired
        private DataSource dataSource;

        @Test
        public void testFindWithUserId() {
            Long userId = 1L;
            List<TemporaryTransfer> temporaryTransferList = repository.find(userId);
            assertEquals(2, temporaryTransferList.size());
            assertEquals(1L, temporaryTransferList.get(0).getId());
            assertEquals(1L, temporaryTransferList.get(0).getFromAccount());
            assertEquals(2L, temporaryTransferList.get(0).getToAccount());
            assertEquals("desc1", temporaryTransferList.get(0).getDescription());
            assertEquals(4800, temporaryTransferList.get(0).getAmount());
            assertEquals(1L, temporaryTransferList.get(0).getUserId());
            assertEquals(2L, temporaryTransferList.get(1).getId());
        }

        @Test
        public void testFindOne() {
            TransferSelector selector = new TransferSelector();
            selector.setId(1L);
            selector.setUserId(1L);
            TemporaryTransfer temporaryTransfer = repository.findOne(selector);
            assertEquals(1L, temporaryTransfer.getId());
            assertEquals(1L, temporaryTransfer.getFromAccount());
            assertEquals(2L, temporaryTransfer.getToAccount());
            assertEquals("desc1", temporaryTransfer.getDescription());
            assertEquals(4800, temporaryTransfer.getAmount());
            assertEquals(1L, temporaryTransfer.getUserId());
        }

        @Test
        public void testFindOneAnotherUserTemporary() {
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
        private TemporaryTransferRepository repository;
        @Autowired
        private DataSource source;

        private final File insertExpectedData  = new File(DATA_DIR + "temporary_insert_expected.xlsx");

        @Test
        public void testInsert() {
            TemporaryTransfer temporaryTransfer = TestDomainDataCreator.temporaryCreate(
                    4L, 4L, 5L, 3500, "desc4", 2L, 3L);
            repository.add(temporaryTransfer);
            DbUnitUtil.assertMutateResult(
                    source, "TEMPORARY_TRANSFER",
                    insertExpectedData, Arrays.asList());
        }

    }

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class UpdateDbTest {

        @Autowired
        private TemporaryTransferRepository repository;
        @Autowired
        private DataSource source;

        private final File updateExpectedData  = new File(DATA_DIR + "temporary_update_expected.xlsx");
        private final File setNullExpectedData = new File(DATA_DIR + "temporary_setNull_expected.xlsx");

        @Test
        public void testUpdate() {
            TemporaryTransfer temporaryTransfer = TestDomainDataCreator.temporaryCreate(
                    3L, 4L, 5L, 3000, "desc3", 2L, 3L);
            repository.set(temporaryTransfer);
            DbUnitUtil.assertMutateResult(
                    source, "TEMPORARY_TRANSFER",
                    updateExpectedData, Arrays.asList());
        }

        @Test
        public void testSetNull() {
            repository.setNullAccount(2L);
            DbUnitUtil.assertMutateResult(source, "TEMPORARY_TRANSFER", setNullExpectedData, List.of());
        }

    }

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class DeleteDbTest {

        @Autowired
        private TemporaryTransferRepository repository;
        @Autowired
        private DataSource source;

        private final File deleteExpectedData  = new File(DATA_DIR + "temporary_delete_expected.xlsx");

        @Test
        public void testDelete() {
            TemporaryTransferSelector selector = new TemporaryTransferSelector();
            selector.setUserId(2L);
            selector.setId(3L);
            repository.delete(selector);
            DbUnitUtil.assertMutateResult(
                    source, "TEMPORARY_TRANSFER",
                    deleteExpectedData, Arrays.asList());
        }

    }

}
