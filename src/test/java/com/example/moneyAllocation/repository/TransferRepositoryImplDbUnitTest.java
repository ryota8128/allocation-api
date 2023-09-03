package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.moneyAllocation.MoneyAllocationApplication;
import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.service.TransferService;
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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class TransferRepositoryImplDbUnitTest {
  private final String DATA_DIR =
      this.getClass().getResource("").getFile() + "../data" + File.separator;

  @SpringBootTest(classes = MoneyAllocationApplication.class)
  @TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DbTestExecutionListener.class
  })
  @Nested
  public class FindDbTest {
    @Autowired private TransferRepository repository;

    @Autowired private DataSource dataSource;

    @Test
    public void testFindList() {
      Long userId = 1L;
      List<Transfer> findResult = repository.find(userId);

      assertEquals(2, findResult.size());
      assertEquals(1L, findResult.get(0).getId());
      assertEquals("2023/06", findResult.get(0).getTitle());
      assertEquals(1L, findResult.get(0).getUserId());
      assertEquals(2L, findResult.get(1).getId());
      assertEquals("2023/07", findResult.get(1).getTitle());
      assertEquals(1L, findResult.get(1).getUserId());
    }

    @Test
    public void testFindEmptyList() {
      Long userId = 100L;
      List<Transfer> findResult = repository.find(userId);
      assertEquals(0, findResult.size());
    }

    @Test
    void testFindOne() {
      TransferSelector selector = TransferSelector.withId(3L, 2L);
      Transfer findResult = repository.findOne(selector);
      assertEquals(3L, findResult.getId());
      assertEquals(2L, findResult.getUserId());
      assertEquals("2022/09", findResult.getTitle());
    }

    @Test
    void testFindOneIdNotExist() {
      TransferSelector selector = TransferSelector.withId(100L, 2L);
      assertThrows(ResourceNotFoundException.class, () -> repository.findOne(selector));
    }

    @Test
    void testFindOneUserIdNotExist() {
      TransferSelector selector = TransferSelector.withId(1L, 100L);
      assertThrows(ResourceNotFoundException.class, () -> repository.findOne(selector));
    }
  }

  @SpringBootTest(classes = MoneyAllocationApplication.class)
  @TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DbTestExecutionListener.class
  })
  @Nested
  public class InsertDbTest {
    @Autowired private TransferRepository repository;

    @Autowired DataSource dataSource;

    private final File insertExpectedData = new File(DATA_DIR + "transfer_insert_expected.xlsx");

    @Test
    void testInsert() {
      Transfer transfer = new Transfer();
      transfer.setTitle("2023/12");
      transfer.setUserId(1L);

      repository.add(transfer);
      DbUnitUtil.assertMutateResult(
          dataSource, "TRANSFER", insertExpectedData, Arrays.asList("ID"));
    }

    @Test
    void testInsertInvalidUserId() {
      Transfer transfer = new Transfer();
      transfer.setTitle("2023/12");
      transfer.setUserId(1000L);

      assertThrows(RuntimeException.class, () -> repository.add(transfer));
    }
  }

  @SpringBootTest(classes = MoneyAllocationApplication.class)
  @TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DbTestExecutionListener.class
  })
  @Nested
  public class UpdateDbTest {
    @Autowired private TransferRepository repository;
    @Autowired private DataSource dataSource;

    private final File updateExpectedData = new File(DATA_DIR + "transfer_update_expected.xlsx");

    @Test
    void testUpdate() {
      Transfer transfer = new Transfer();
      transfer.setId(2L);
      transfer.setTitle("2023/08");
      transfer.setUserId(1L);
      repository.set(transfer);
      DbUnitUtil.assertMutateResult(dataSource, "TRANSFER", updateExpectedData, Arrays.asList());
    }

    @Test
    void testUpdateInvalidUserId() {
      Transfer transfer = new Transfer();
      transfer.setId(2L);
      transfer.setTitle("2023/08");
      transfer.setUserId(1000L);
      assertThrows(ResourceNotFoundException.class, () -> repository.set(transfer));
    }

    @Test
    void testUpdateInvalidId() {
      Transfer transfer = new Transfer();
      transfer.setId(1000L);
      transfer.setTitle("2023/08");
      transfer.setUserId(1L);
      assertThrows(ResourceNotFoundException.class, () -> repository.set(transfer));
    }
  }

  @SpringBootTest(classes = MoneyAllocationApplication.class)
  @TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DbTestExecutionListener.class
  })
  @Nested
  public class DeleteDbTest {
    @Autowired private TransferRepository repository;
    @Autowired private DataSource dataSource;

    @Autowired private TransferService transferService;

    private final File deleteExpectedData = new File(DATA_DIR + "transfer_delete_expected.xlsx");

    @Test
    void testDelete() {
      TransferSelector selector = TransferSelector.withId(2L, 1L);
      transferService.delete(selector);
      DbUnitUtil.assertMutateResult(dataSource, "TRANSFER", deleteExpectedData, Arrays.asList());
      DbUnitUtil.assertMutateResult(
          dataSource, "TEMPORARY_TRANSFER", deleteExpectedData, Arrays.asList());
    }
  }
}
