package com.doubao.finance.util.excel.parser;

import com.doubao.finance.util.excel.exception.ExcelParseException;
import com.google.common.collect.ImmutableList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExcelParser
        implements ExcelParser
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExcelParser.class);
    private static final int DEFAULT_ROW_INDEX = 1;
    private Map<Integer, String> titleMap = new HashMap();
    private List<Map<String, String>> data = new ArrayList();

    public List<Map<String, String>> getData()
    {
        return ImmutableList.copyOf(this.data);
    }

    public List<String> getTitle()
    {
        return ImmutableList.copyOf(this.titleMap.values());
    }

    public int getTotalRowNumber()
    {
        return getData().size();
    }

    public int getTotalColumnNumber()
    {
        return getTitle().size();
    }

    public void parseExcel(String fileName, int sheetIndex)
            throws ExcelParseException
    {
        try
        {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            parseExcel(fileInputStream, sheetIndex);
        }
        catch (FileNotFoundException e)
        {
            throw new ExcelParseException("file:[" + fileName + "] not exist!", e);
        }
    }

    public void parseExcel(InputStream inputStream, int sheetIndex)
            throws ExcelParseException
    {
        Workbook workbook = null;
        try
        {
            workbook = WorkbookFactory.create(inputStream);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
            throw new ExcelParseException(e.getMessage(), e);
        }
        int numberOfSheets = workbook.getNumberOfSheets();
        if (sheetIndex > numberOfSheets)
        {
            LOGGER.error("invalid sheetIndex[{}] ,number of sheets [{}]", Integer.valueOf(sheetIndex), Integer.valueOf(numberOfSheets));
            throw new ExcelParseException("the number of sheets is:" + numberOfSheets + " ,but you want to access :" + sheetIndex);
        }
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("total row: {}", Integer.valueOf(lastRowNum - firstRowNum + 1));
        }
        parseHeader(sheet);
        parseData(sheet, 1);
    }

    private void parseData(Sheet sheet, int startRow)
            throws ExcelParseException
    {
        int lastRowNum = sheet.getLastRowNum();

        Map<String, String> rowData = null;
        for (int rowNum = startRow; rowNum <= lastRowNum; rowNum++)
        {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("start parsing row:{}", Integer.valueOf(rowNum));
            }
            Row sheetRow = sheet.getRow(rowNum);
            int lastCellNum = sheetRow.getLastCellNum();
            lastCellNum = Math.max(lastCellNum, getTotalColumnNumber());
            short firstCellNum = sheetRow.getFirstCellNum();
            rowData = new HashMap(lastCellNum);
            for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++)
            {
                String title = (String)this.titleMap.get(Integer.valueOf(cellNum));
                rowData.put(title, getCellValue(sheetRow, cellNum));
            }
            this.data.add(rowData);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("parsing row:{} done.", Integer.valueOf(rowNum));
            }
        }
    }

    private String getCellValue(Row sheetRow, int cellNum)
    {
        Cell cell = sheetRow.getCell(cellNum);
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType())
        {
            case 3:
                return "";
            case 4:
                return String.valueOf(cell.getBooleanCellValue());
            case 1:
                return cell.getStringCellValue().trim();
            case 0:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd HH:mm:ss").trim();
                }
                return String.valueOf(cell.getNumericCellValue()).trim();
        }
        return "";
    }

    private void parseHeader(Sheet sheet)
            throws ExcelParseException
    {
        int firstRowNum = sheet.getFirstRowNum();
        Row sheetRow = sheet.getRow(firstRowNum);
        short lastCellNum = sheetRow.getLastCellNum();
        short firstCellNum = sheetRow.getFirstCellNum();
        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++)
        {
            Cell cell = sheetRow.getCell(cellNum);
            String cellValue = cell.getStringCellValue();
            if (this.titleMap.containsValue(cellValue))
            {
                LOGGER.error("found duplicated title:{} from the excel", cellValue);
                throw new ExcelParseException("found duplicated title : " + cellValue + " from the excel");
            }
            this.titleMap.put(Integer.valueOf(cellNum), cellValue);
        }
    }
}
