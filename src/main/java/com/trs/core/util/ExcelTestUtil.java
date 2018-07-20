package com.trs.core.util;

/**
 * Created by 李春雨 on 2017/3/16.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @ClassName: ReadExcelUtils
 * @Description: 读取excel(兼容03和07格式)
 * @author
 * @company
 * @date 2015年12月18日
 * @version V1.0
 */

public class ExcelTestUtil {

    private static final Logger log = LoggerFactory
            .getLogger(ExcelTestUtil.class);

    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";

    /**
     * 读取excel文件内容
     *
     * @param filePath
     * @throws Exception
     */
    public List<Map<String, String>> readExcel(String filePath)
            throws Exception {

        log.info("Start to read excel file:" + filePath);

        List<Map<String, String>> list = new LinkedList<Map<String, String>>();

        // 检查
        this.preReadCheck(filePath);
        // 获取workbook对象
        Workbook workbook = null;

        workbook = this.getWorkbook(filePath);
        // 读文件 一个sheet一个sheet地读取
        for (int numSheet = 0; numSheet < workbook
                .getNumberOfSheets(); numSheet++) {
            Sheet sheet = workbook.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }

            log.info("=======================" + sheet.getSheetName()
                    + "=========================");

            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();

            // 读取首行 即,表头
            Row firstRow = sheet.getRow(firstRowIndex);

            log.info("firstRow=" + firstRow);

            if (null != firstRow) {

                ConcurrentMap<String, String> map = null;

                // 读取数据行
                for (int rowIndex = firstRowIndex
                        + 1; rowIndex <= lastRowIndex; rowIndex++) {
                    // 当前行
                    Row currentRow = sheet.getRow(rowIndex);
                    // 首列
                    int firstColumnIndex = currentRow.getFirstCellNum();
                    // 最后一列
                    int lastColumnIndex = currentRow.getLastCellNum();

                    for (int columnIndex = firstColumnIndex; columnIndex <= lastColumnIndex; columnIndex++) {
                        // 标题列
                        Cell titleCell = firstRow.getCell(columnIndex);
                        // 标题列对应的值
                        String titleCellValue = this.getCellValue(titleCell,
                                true);

                        // 当前单元格
                        Cell currentCell = currentRow.getCell(columnIndex);
                        // 当前单元格的值
                        String currentCellValue = this.getCellValue(currentCell,
                                true);

                        map = new ConcurrentHashMap<String, String>(16);

                        map.put(titleCellValue, currentCellValue);

                        list.add(map);
                    }
                }
            } // end of if (null != firstRow)
        }

        log.info("Read excel file '" + filePath + "' finished.");

        return list;
    }

    /***
     * <pre>
     * 取得Workbook对象(xls和xlsx对象不同,不过都是Workbook的实现类)
     *   xls:HSSFWorkbook
     *   xlsx：XSSFWorkbook
     * &#64;param filePath
     * &#64;return
     * &#64;throws IOException
     * </pre>
     */
    private Workbook getWorkbook(String filePath) throws IOException {

        Workbook workbook = null;
        InputStream is = null;

        try {
            is = new FileInputStream(filePath);

            if (filePath.endsWith(EXTENSION_XLS)) {
                workbook = new HSSFWorkbook(is);
            } else if (filePath.endsWith(EXTENSION_XLSX)) {
                workbook = new XSSFWorkbook(is);
            }

        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            is.close();
        }

        return workbook;
    }

    /**
     * 文件检查
     *
     * @param filePath
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
    private void preReadCheck(String filePath)
            throws FileNotFoundException, FileFormatException {
        // 常规检查
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("传入的文件不存在：" + filePath);
        }

        if (!(filePath.endsWith(EXTENSION_XLS)
                || filePath.endsWith(EXTENSION_XLSX))) {
            throw new FileFormatException("传入的文件不是excel");
        }
    }

    /**
     * 取单元格的值
     *
     * @param cell
     *            单元格对象
     * @param treatAsStr
     *            为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
     * @return
     */
    private String getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }

        if (treatAsStr) {
            // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
            // 加上下面这句，临时把它当做文本来读取
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }

        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }
}