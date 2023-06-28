package com.example.moneyAllocation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.operation.DatabaseOperation;

public class DbUnitUtil {
    public static void loadData(DataSource source, File dataFile) {
        IDatabaseConnection connection = null;
        try {
            connection = dbUnitConnection(source);
            DatabaseConfig config = connection.getConfig();
            config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
            FileInputStream inFile = new FileInputStream(dataFile);
            XlsDataSet dataSet = new XlsDataSet(inFile);
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static IDatabaseConnection dbUnitConnection(DataSource source) throws Exception {
        return new H2Connection(source.getConnection(), "ALLOCATION");
    }

    public static void backup(DataSource dataSource, File backup, String... tableNames) {
        IDatabaseConnection connection = null;
        try {
            connection = dbUnitConnection(dataSource);
            IDataSet partialDataSet = connection.createDataSet(tableNames);
            XlsDataSet.write(partialDataSet, new FileOutputStream(backup));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
    }

    public static void deleteAll(DataSource dataSource, File dataFile) {
        IDatabaseConnection connection = null;
        try {
            connection = dbUnitConnection(dataSource);
            FileInputStream inFile = new FileInputStream(dataFile);
            XlsDataSet dataSet = new XlsDataSet(inFile);
            DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
    }

    public static void restoreBackup(DataSource dataSource, File backup) {
        restoreBackup(dataSource, backup, false);
    }
    
    public static void restoreBackup(DataSource dataSource, File backup, boolean deleteOnExit) {
        loadData(dataSource, backup);
        if (deleteOnExit) {
            backup.deleteOnExit();
        } else {
            backup.delete();
        }
    }
}