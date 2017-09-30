package com.doubao.finance.util.excel.converter;

import com.doubao.finance.util.excel.CallbackForConverter;
import com.doubao.finance.util.excel.ErrorTemplate;
import com.doubao.finance.util.excel.annotation.ExcelColumnMapping;
import com.doubao.finance.util.excel.annotation.ExcelEntity;
import com.doubao.finance.util.excel.exception.ExcelConvertException;
import com.doubao.finance.util.excel.validator.ExcelRuleValidator;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.UnmodifiableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExcel2EntityConverter<T>
        implements Excel2EntityConverter<T>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExcel2EntityConverter.class);
    private Class<T> tClass;
    private int maxNumOfError;
    private CallbackForConverter callbackForConverter;
    private List<ErrorTemplate> errorTemplates = new ArrayList();
    private Object data;

    public DefaultExcel2EntityConverter(Class<T> tClass, CallbackForConverter<T> callbackForConverter, Object data)
    {
        this.tClass = tClass;
        this.callbackForConverter = callbackForConverter;
        this.data = data;
    }

    public List<ErrorTemplate> getErrorTemplates()
    {
        return this.errorTemplates;
    }

    public List<T> convert(List<Map<String, String>> source)
            throws ExcelConvertException
    {
        boolean annotationPresent = this.tClass.isAnnotationPresent(ExcelEntity.class);
        if (!annotationPresent) {
            throw new ExcelConvertException("no annotation��@ExcelEntity found in class:" + this.tClass.getName());
        }
        ExcelEntity excelEntity = (ExcelEntity)this.tClass.getAnnotation(ExcelEntity.class);
        this.maxNumOfError = excelEntity.maxNumOfError();
        Class<? extends ExcelRuleValidator> validatorClass = excelEntity.validator();
        if ((source == null) || (source.isEmpty())) {
            return Lists.newArrayList();
        }
        List<T> list = new ArrayList(source.size());
        Map<String, Field> excelColumnFieldMapping = getExcelColumnMapping(this.tClass);

        int errorCount = 0;
        try
        {
            for (int row = 0; row < source.size(); row++)
            {
                boolean allColumnRight = true;
                Map<String, String> rowData = (Map)source.get(row);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("current row:{},data:{}", Integer.valueOf(row + 2), rowData);
                }
                if (this.callbackForConverter != null)
                {
                    this.callbackForConverter.beforeEachRow(rowData, row + 2);
                    if (!this.callbackForConverter.accecp(rowData, row + 2)) {}
                }
                else
                {
                    T instance = createInstance();
                    for (Map.Entry<String, String> columnNameAndValue : rowData.entrySet())
                    {
                        String column = null;
                        if (errorCount >= this.maxNumOfError)
                        {
                            this.errorTemplates.add(new ErrorTemplate(row + 2, "", "��������������:[" + this.maxNumOfError + "]������������"));
                            throw new ExcelConvertException("��������������:[" + this.maxNumOfError + "]������������");
                        }
                        try
                        {
                            column = (String)columnNameAndValue.getKey();
                            String value = (String)columnNameAndValue.getValue();
                            if (!excelColumnFieldMapping.containsKey(column))
                            {
                                LOGGER.warn("����[{}]����������������������������������", column);
                            }
                            else
                            {
                                Field field = (Field)excelColumnFieldMapping.get(column);
                                ExcelColumnMapping columnMapping = (ExcelColumnMapping)field.getAnnotation(ExcelColumnMapping.class);
                                boolean required = columnMapping.required();
                                String defaultValue = columnMapping.defaultValue();
                                if (required)
                                {
                                    if (StringUtils.isBlank(value)) {
                                        throw new ExcelConvertException("������������");
                                    }
                                }
                                else if (StringUtils.isBlank(value)) {
                                    value = defaultValue;
                                }
                                tryCheck(validatorClass, rowData, row, column, value);

                                setFieldValue(instance, field, value);
                            }
                        }
                        catch (ExcelConvertException e)
                        {
                            errorCount++;
                            allColumnRight = false;
                            ErrorTemplate tm = new ErrorTemplate(row + 2, column, e.getMessage());
                            this.errorTemplates.add(tm);
                        }
                    }
                    if (allColumnRight) {
                        list.add(instance);
                    }
                }
            }
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        if (this.callbackForConverter != null) {
            this.callbackForConverter.onFinish(list, this.errorTemplates, source);
        }
        return list;
    }

    private void tryCheck(Class<? extends ExcelRuleValidator> validatorClass, Map<String, String> rowData, int row, String column, String value)
            throws ExcelConvertException
    {
        if (validatorClass != null)
        {
            ExcelRuleValidator ruleValidator = null;
            try
            {
                ruleValidator = (ExcelRuleValidator)validatorClass.newInstance();
            }
            catch (Exception e)
            {
                throw new RuntimeException("��������������������");
            }
            if (this.data != null) {
                ruleValidator.setData(this.data);
            }
            Map<String, String> stringStringMap = UnmodifiableMap.unmodifiableMap(rowData);
            ruleValidator.check((UnmodifiableMap)stringStringMap, row + 2, column, value);
        }
    }

    private T createInstance()
            throws IllegalAccessException, InstantiationException
    {
        T instance = this.tClass.newInstance();
        return instance;
    }

    private T setFieldValue(T instance, Field field, String value)
            throws ExcelConvertException
    {
        Class<?> fieldType = field.getType();
        Object result = "";
        if (fieldType.isPrimitive())
        {
            if (fieldType == Integer.TYPE) {
                result = Integer.valueOf(new BigDecimal(value).intValue());
            } else if (fieldType == Double.TYPE) {
                result = Double.valueOf(value);
            } else if (fieldType == Float.TYPE) {
                result = Float.valueOf(value);
            } else if (fieldType == Long.TYPE) {
                result = Long.valueOf(value);
            } else if (fieldType == Boolean.TYPE) {
                result = Boolean.valueOf(value);
            } else if (fieldType == Short.TYPE) {
                result = Short.valueOf(value);
            } else if (fieldType == Byte.TYPE) {
                result = Byte.valueOf(value);
            }
        }
        else if (fieldType == String.class) {
            result = value;
        } else if (fieldType == Date.class) {
            try
            {
                result = SimpleDateFormat.getDateTimeInstance().parse(value);
            }
            catch (ParseException e)
            {
                throw new ExcelConvertException(e.getMessage(), e);
            }
        } else if (fieldType == BigDecimal.class) {
            result = new BigDecimal(value);
        } else if (!fieldType.isArray()) {}
        field.setAccessible(true);
        try
        {
            field.set(instance, result);
            return instance;
        }
        catch (Exception e)
        {
            throw new ExcelConvertException(e.getMessage(), e);
        }
    }

    private static Map<String, Field> getExcelColumnMapping(Class tClass)
            throws ExcelConvertException
    {
        List<Field> listWithAnnotation = FieldUtils.getFieldsListWithAnnotation(tClass, ExcelColumnMapping.class);
        if (CollectionUtils.isEmpty(listWithAnnotation)) {
            throw new ExcelConvertException("no annotation:@ExcelColumnMapping found in any field of type :" + tClass.getName());
        }
        Map<String, Field> map = new HashMap();
        for (Field field : listWithAnnotation)
        {
            ExcelColumnMapping excelColumnMapping = (ExcelColumnMapping)field.getAnnotation(ExcelColumnMapping.class);
            String column = excelColumnMapping.column();
            if (map.containsKey(column))
            {
                LOGGER.error("found duplicated column :{},please check that...", column);
                throw new ExcelConvertException("found duplicated column:" + column + ",please check that...");
            }
            map.put(column, field);
        }
        return map;
    }
}
