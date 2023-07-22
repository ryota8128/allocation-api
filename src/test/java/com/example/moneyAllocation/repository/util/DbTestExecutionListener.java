package com.example.moneyAllocation.repository.util;

import com.example.moneyAllocation.util.DbUnitUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class DbTestExecutionListener extends AbstractTestExecutionListener {
    public static final String DATA_DIR =
        DbTestExecutionListener.class.getResource("").getFile()
            + ".." + File.separator + "data" + File.separator;

    private static final File INIT_DATA = new File(DATA_DIR + "init.xlsx");
    private static final List<File> backupList = new ArrayList<>();

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        DataSource dataSource = testContext.getApplicationContext().getBean(DataSource.class);
        Arrays.asList("TEMPORARY_TRANSFER", "REGULAR_TRANSFER", "TRANSFER", "ACCOUNT", "USER").forEach(filename -> {
            File file = new File(DATA_DIR + filename + "_back.xls");
            backupList.add(file);
            DbUnitUtil.backup(dataSource, file, filename);
        });
        DbUnitUtil.loadData(dataSource, INIT_DATA);
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        DataSource dataSource = testContext.getApplicationContext().getBean(DataSource.class);
        backupList.forEach(file -> DbUnitUtil.deleteAll(dataSource, file));
        Arrays.asList("USER", "ACCOUNT", "TRANSFER", "REGULAR_TRANSFER", "TEMPORARY_TRANSFER").forEach(filename ->
                DbUnitUtil.restoreBackup(dataSource, new File(DATA_DIR + filename + "_back.xls")));
    }
}
