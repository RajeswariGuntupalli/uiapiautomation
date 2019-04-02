package com.test.dataset;

import com.test.logger.ScenarioLogger;
import org.apache.commons.logging.Log;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ExcelUtils {
    FileInputStream excelFile;
    private XSSFSheet excelWSheet;
    private XSSFWorkbook excelWBook;
    private XSSFCell cell = null;
    private XSSFRow row;
    private String filePath;

    public ExcelUtils() {
    }

    public ExcelUtils(String path, String SheetName) throws Exception {
        excelFile = new FileInputStream(path);
        excelWBook = new XSSFWorkbook(excelFile);
        excelWSheet = excelWBook.getSheet(SheetName);
        filePath = path;
    }

    public void closeFile() throws Exception {
        excelWBook.close();
        excelFile.close();
        excelWSheet = null;
        excelWBook = null;
        excelFile = null;
        System.gc();
    }

    public String getCellData(int RowNum, int ColNum) throws Exception {
        try {

            cell = excelWSheet.getRow(RowNum).getCell(ColNum);
            cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            String CellData = cell.getStringCellValue();
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    public int getRowCount() throws Exception {

        try {
            int rowCount = excelWSheet.getLastRowNum() + 1;
            return rowCount;
        } catch (Exception e) {
            return -1;
        }

    }

    public String getCellData(String RowName, int colNum)
            throws Exception {
        try {
            int MaxRowNumber = excelWSheet.getLastRowNum();
            int rowNum = 0;

            for (int i = 0; i <= MaxRowNumber; i++) {
                if (excelWSheet.getRow(i).getCell(0) != null) {
                    if (excelWSheet.getRow(i).getCell(0).getStringCellValue()
                            .equalsIgnoreCase(RowName)) {
                        rowNum = i;
                        break;
                    }
                } else {
                    continue;
                }
            }
            cell = excelWSheet.getRow(rowNum).getCell(colNum);
            cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            String CellData = cell.getStringCellValue();
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    public String getCellData(String RowName, String ColName)
            throws Exception {
        int MaxRowNumber = excelWSheet.getLastRowNum();
        int MaxColNumber = 100;
        int rowNum = 0;
        int colNum = 0;
        for (int i = 1; i <= MaxColNumber; i++) {
            cell = excelWSheet.getRow(0).getCell(i);
            cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            if (cell != null) {
                if (cell.toString().trim().equalsIgnoreCase(ColName)) {
                    colNum = i;
                    break;
                }
            } else {
                continue;
            }
        }
        for (int i = 1; i <= MaxRowNumber; i++) {
            cell = excelWSheet.getRow(i).getCell(0);
            cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            if (cell != null) {
                if (cell.toString().trim().equalsIgnoreCase(RowName)) {
                    rowNum = i;
                    break;
                }
            } else {
                continue;
            }
        }
        cell = excelWSheet.getRow(rowNum).getCell(colNum);
        String CellData = "";
        if (cell != null) {

            cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            CellData = cell.getStringCellValue().trim();
        } else {
            CellData = "";
        }

        return CellData;
    }

    public int getColumnNumber(String ColName) {
        try {

            int MaxColNumber = 100;
            int colNum = 0;

            for (int i = 0; i < MaxColNumber; i++) {
                cell = excelWSheet.getRow(0).getCell(i);
                if (cell != null) {
                    if (cell.toString().equalsIgnoreCase(ColName)) {
                        colNum = i;
                        break;
                    }
                }
            }
            return colNum;
        } catch (Exception e) {
            return -1;
        }
    }

    public int getMaxColNumber() throws Exception {
        try {

            int MaxColNumber = excelWSheet.getRow(0).getLastCellNum();
            return MaxColNumber;
        } catch (Exception e) {
            return -1;
        }
    }

    public ArrayList<String[]> getDataSet() throws Exception {
        try {

            int MaxRowNumber = excelWSheet.getLastRowNum();
            int MaxColNumber = getMaxColNumber();
            ArrayList<String[]> dataset = new ArrayList<String[]>();
            String[] arr = new String[MaxColNumber];
            for (int i = 0; i < MaxRowNumber; i++) {
                for (int j = 0; j < MaxColNumber; j++) {
                    cell = excelWSheet.getRow(i).getCell(j);
                    if (cell == null) {
                        cell.setCellValue("");
                    }

                    String str = getCellData(i, j);
                    arr[i] = str;

                }
                dataset.add(arr);
            }
            return dataset;

        } catch (Exception e) {
            return null;
        }

    }
}