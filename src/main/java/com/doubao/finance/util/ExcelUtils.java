package com.doubao.finance.util;

import com.doubao.finance.util.excel.CallbackForConverter;
import com.doubao.finance.util.excel.annotation.ExportEntity;
import com.doubao.finance.util.excel.annotation.ExportField;
import com.doubao.finance.util.excel.converter.DefaultExcel2EntityConverter;
import com.doubao.finance.util.excel.converter.Excel2EntityConverter;
import com.doubao.finance.util.excel.exception.ExcelConvertException;
import com.doubao.finance.util.excel.exception.ExcelParseException;
import com.doubao.finance.util.excel.parser.DefaultExcelParser;
import com.doubao.finance.util.excel.parser.ExcelParser;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class ExcelUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);
    private static final int DEFAULT_SHEET_INDEX = 0;

    public static <T> List<T> xls2Entity(InputStream inputStream, Class<T> tClass, CallbackForConverter callbackForConverter, Object data)
            throws ExcelConvertException, ExcelParseException
    {
        if (tClass == null) {
            throw new IllegalArgumentException();
        }
        ExcelParser excelParser = new DefaultExcelParser();
        excelParser.parseExcel(inputStream, 0);
        List<Map<String, String>> excelParserData = excelParser.getData();
        Excel2EntityConverter<T> converter = new DefaultExcel2EntityConverter(tClass, callbackForConverter, data);
        List<T> personList = converter.convert(excelParserData);
        return personList;
    }

    public static <T> List<T> xls2Entity(String fileName, Class<T> tClass, CallbackForConverter callbackForConverter, Object data)
            throws ExcelConvertException, ExcelParseException
    {
        try
        {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            return xls2Entity(fileInputStream, tClass, callbackForConverter, data);
        }
        catch (FileNotFoundException e)
        {
            throw new ExcelParseException("file:[" + fileName + "] does not exists!");
        }
    }

    public static void export(Workbook workbook, Collection<?> dataList, Class<?> clazz)
            throws Exception
    {
        if (!clazz.isAnnotationPresent(ExportEntity.class)) {
            throw new RuntimeException("must add @ExportEntity");
        }
        ExportEntity exportEntity = (ExportEntity)clazz.getAnnotation(ExportEntity.class);

        Sheet sheet = workbook.createSheet(exportEntity.sheetName());

        writeTitleRow(sheet, clazz.getDeclaredFields());
        int rowIndex = 1;
        for (Object data : dataList)
        {
            if (data.getClass() != clazz) {
                throw new RuntimeException("class 不兼容");
            }
            Field[] fields = data.getClass().getDeclaredFields();

            Row row = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            for (Field field : fields) {
                if (field.isAnnotationPresent(ExportField.class))
                {
                    Cell cell = row.createCell(cellIndex++);
                    field.setAccessible(true);
                    ExportField exportField = (ExportField)field.getAnnotation(ExportField.class);
                    Class<? extends Enum> refer = exportField.refer();
                    if ((refer.isEnum()) && (refer != Enum.class))
                    {
                        Method method = refer.getDeclaredMethod("valueOf", new Class[] { Integer.TYPE });
                        String invoke = (String)method.invoke(null, new Object[] { field.get(data) });
                        cell.setCellType(1);
                        cell.setCellValue(invoke);
                    }
                    else if (field.getType() == Boolean.class)
                    {
                        cell.setCellValue(field.getBoolean(data));
                        cell.setCellType(4);
                    }
                    else if ((field.getType() == Double.class) ||
                            (field.getType() == Double.TYPE) ||
                            (field.getType() == Integer.class) ||
                            (field.getType() == Integer.TYPE) ||
                            (field.getType() == Long.class) ||
                            (field.getType() == Long.TYPE) ||
                            (field.getType() == Short.class) ||
                            (field.getType() == Short.TYPE) ||
                            (field.getType() == Byte.class) ||
                            (field.getType() == Byte.TYPE) ||
                            (field.getType() == Float.class) ||
                            (field.getType() == Float.TYPE))
                    {
                        cell.setCellType(0);
                        cell.setCellValue(field.getDouble(data));
                    }
                    else if (field.getType() == BigDecimal.class)
                    {
                        BigDecimal val = (BigDecimal)field.get(data);
                        cell.setCellType(0);
                        cell.setCellValue(val.setScale(2, 4).doubleValue());
                    }
                    else if (field.getType() == String.class)
                    {
                        cell.setCellType(1);
                        cell.setCellValue(field.get(data).toString());
                    }
                    else if (field.getType() == Date.class)
                    {
                        cell.setCellType(1);
                        Date value = (Date)field.get(data);
                        cell.setCellValue(DateFormatUtils.format(value, "yyyy-MM-dd HH:mm:ss"));
                    }
                    else if (field.getType() == Calendar.class)
                    {
                        Calendar value = (Calendar)field.get(data);
                        cell.setCellType(1);
                        cell.setCellValue(DateFormatUtils.format(value, "yyyy-MM-dd HH:mm:ss"));
                    }
                    else
                    {
                        throw new UnsupportedOperationException("目前不支持复合类型导出！");
                    }
                }
            }
        }
    }

    private static void writeTitleRow(Sheet sheet, Field[] fields)
    {
        int i = 0;
        Row row = sheet.createRow(0);
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExportField.class))
            {
                ExportField exportField = (ExportField)field.getAnnotation(ExportField.class);
                Cell cell = row.createCell(i++, 1);
                cell.setCellValue(exportField.title());
            }
        }
    }
}
