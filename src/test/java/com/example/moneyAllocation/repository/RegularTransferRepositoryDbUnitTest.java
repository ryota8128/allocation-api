package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.example.moneyAllocation.MoneyAllocationApplication;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
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

public class RegularTransferRepositoryDbUnitTest {
    private final String DATA_DIR = this.getClass().getResource("")
            .getFile() + "data" + File.separator;

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class FindDbTest {
        @Autowired
        private RegularTransferRepository repository;
        @Autowired
        private DataSource source;

        @Test
        public void testFindAll() {
            RegularTransferSelector selector = new RegularTransferSelector();
            List<RegularTransfer> regularTransferList = repository.find(selector);
            assertEquals(3, regularTransferList.size());
            assertEquals(1L, regularTransferList.get(0).getId());
            assertEquals(1L, regularTransferList.get(0).getFromAccount());
            assertEquals(2L, regularTransferList.get(0).getToAccount());
            assertEquals("desc1", regularTransferList.get(0).getDescription());
            assertEquals(false, regularTransferList.get(0).getPercentage());
            assertEquals(30000, regularTransferList.get(0).getAmount());
            assertNull(regularTransferList.get(0).getRatio());
            assertEquals(1L, regularTransferList.get(0).getUserId());

            assertEquals(2L, regularTransferList.get(1).getId());
            assertEquals(true, regularTransferList.get(1).getPercentage());
            assertNull(regularTransferList.get(1).getAmount());
            assertEquals((float) 0.4, regularTransferList.get(1).getRatio());

            assertEquals(3L, regularTransferList.get(2).getId());
            assertEquals(false, regularTransferList.get(2).getPercentage());
            assertEquals(25000, regularTransferList.get(2).getAmount());
            assertNull(regularTransferList.get(2).getRatio());
        }

        @Test
        public void testFindWithUserId() {
            RegularTransferSelector selector = new RegularTransferSelector();
            selector.setUserId(1L);
            List<RegularTransfer> regularTransferList = repository.find(selector);
            assertEquals(2, regularTransferList.size());
            assertEquals(1L, regularTransferList.get(0).getId());
            assertEquals(1L, regularTransferList.get(0).getFromAccount());
            assertEquals(2L, regularTransferList.get(0).getToAccount());
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
    }
}
