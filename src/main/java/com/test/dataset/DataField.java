package com.test.dataset;

import com.test.utilities.CommonUtils;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

public class DataField {

    private static final Logger log = Logger.getLogger(DataField.class);
    public String testCaseName = null;
    public ExcelUtils excelDataPool = null;
    private HashMap<String, Object> cache = null;

    public DataField(ExcelUtils dataPool, String strTestCaseName) throws Exception {
        try {
            this.excelDataPool = dataPool;
            this.testCaseName = strTestCaseName;
            cache = new HashMap<String, Object>();
            int rowCount = dataPool.getRowCount();
            int colNum = dataPool.getColumnNumber(testCaseName);

            for (int i = 0; i < rowCount; i++) {
                String rowName = dataPool.getCellData(i, 0);
                if (!rowName.isEmpty()) {
                    String inputData = dataPool.getCellData(i, colNum);
                    cache.put(rowName.trim(), inputData.trim());
                }
            }
        } finally {
            excelDataPool.closeFile();
        }
    }

    public String getData(String rowName) throws Exception {
        String cellData = null;
        try {
            if (cache.get(rowName) != null && cache.get(rowName) instanceof String) {
                cellData = CommonUtils.replaceDynamicData((String) cache.get(rowName));
                putData(rowName, cellData);
                return cellData;
            }
        } catch (Exception e) {
            log.error("cannot get data from datapool file for this row name" + rowName, e);
            throw e;
        }
        return cellData;
    }

    public void putData(String rowName, Object value) throws Exception {
        try {
            cache.put(rowName, value);
        } catch (Exception e) {
            log.error("cannot put data into datapool file for this row name" + rowName, e);
            throw e;
        }
    }
}


