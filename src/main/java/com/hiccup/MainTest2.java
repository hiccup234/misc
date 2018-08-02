package com.hiccup;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainTest2 {

    public static void main(String[] args) {
        InputStream inputStream = null;
        try {
            //获取文件标识符。
            inputStream = new FileInputStream(new File("C:\\Users\\haiyang.wen\\Desktop\\test.xlsx"));
            //System.out.println(inputStream);
            excelRead.readExcel("C:\\Users\\haiyang.wen\\Desktop\\test.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

class excelRead {
        public static List<Long> readExcel(String path) throws IOException {
            if (path.endsWith(".xls")) {
                return readXls(path);
            } else {
                return readXlsx(path);
            }
        }

        public static List<Long> readXlsx(String path) throws IOException {
            InputStream is = new FileInputStream(path);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            List<Long> list = new ArrayList<Long>();
            // Read the Sheet
            for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                // 从第0行开始读
                for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if (xssfRow != null) {
                        XSSFCell userId = xssfRow.getCell(0);
                        userId.setCellType(userId.CELL_TYPE_STRING);
                        list.add(Long.valueOf(getValue(userId)));
                    }
                }
            }
            return list;
        }

        public static List<Long> readXls(String path) throws IOException {
            InputStream is = new FileInputStream(path);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            List<Long> list = new ArrayList<Long>();
            // Read the Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // Read the Row
                for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        HSSFCell userId = hssfRow.getCell(0);
                        userId.setCellType(userId.CELL_TYPE_STRING);
                        list.add(Long.valueOf(getValue(userId)));
                    }
                }
            }
            return list;
        }

        @SuppressWarnings("static-access")
        private static String getValue(XSSFCell xssfRow) {
            if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
                return String.valueOf(xssfRow.getBooleanCellValue());
            } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
                return String.valueOf(xssfRow.getNumericCellValue());
            } else {
                return String.valueOf(xssfRow.getStringCellValue());
            }
        }

        @SuppressWarnings("static-access")
        private static String getValue(HSSFCell hssfCell) {
            if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
                return String.valueOf(hssfCell.getBooleanCellValue());
            } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
                return String.valueOf(hssfCell.getNumericCellValue());
            } else {
                return String.valueOf(hssfCell.getStringCellValue());
            }
        }
    }