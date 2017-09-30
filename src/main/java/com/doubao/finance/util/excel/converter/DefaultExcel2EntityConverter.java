//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.doubao.finance.util.excel.converter;

import com.doubao.finance.util.excel.CallbackForConverter;
import com.doubao.finance.util.excel.ErrorTemplate;
import com.doubao.finance.util.excel.annotation.ExcelColumnMapping;
import com.doubao.finance.util.excel.annotation.ExcelEntity;
import com.doubao.finance.util.excel.exception.ExcelConvertException;
import com.doubao.finance.util.excel.validator.ExcelRuleValidator;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.UnmodifiableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class DefaultExcel2EntityConverter<T> implements Excel2EntityConverter<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExcel2EntityConverter.class);
    private Class<T> tClass;
    private int maxNumOfError;
    private CallbackForConverter callbackForConverter;
    private List<ErrorTemplate> errorTemplates = new ArrayList();
    private Object data;

    public DefaultExcel2EntityConverter(Class<T> tClass, CallbackForConverter<T> callbackForConverter, Object data) {
        this.tClass = tClass;
        this.callbackForConverter = callbackForConverter;
        this.data = data;
    }

    private static Map<String, Field> getExcelColumnMapping(Class tClass) throws ExcelConvertException {
        List listWithAnnotation = FieldUtils.getFieldsListWithAnnotation(tClass, ExcelColumnMapping.class);
        if (CollectionUtils.isEmpty(listWithAnnotation)) {
            throw new ExcelConvertException("no annotation:@ExcelColumnMapping found in any field of type :" + tClass.getName());
        } else {
            HashMap map = new HashMap();
            Iterator var3 = listWithAnnotation.iterator();

            while (var3.hasNext()) {
                Field field = (Field) var3.next();
                ExcelColumnMapping excelColumnMapping = (ExcelColumnMapping) field.getAnnotation(ExcelColumnMapping.class);
                String column = excelColumnMapping.column();
                if (map.containsKey(column)) {
                    LOGGER.error("found duplicated column :{},please check that...", column);
                    throw new ExcelConvertException("found duplicated column:" + column + ",please check that...");
                }

                map.put(column, field);
            }

            return map;
        }
    }

    public List<ErrorTemplate> getErrorTemplates() {
        return this.errorTemplates;
    }

    public List<T> convert(List<Map<String, String>> source) throws ExcelConvertException {
        boolean annotationPresent = this.tClass.isAnnotationPresent(ExcelEntity.class);
        if (!annotationPresent) {
            throw new ExcelConvertException("no annotation：@ExcelEntity found in class:" + this.tClass.getName());
        } else {
            ExcelEntity excelEntity = (ExcelEntity) this.tClass.getAnnotation(ExcelEntity.class);
            this.maxNumOfError = excelEntity.maxNumOfError();
            Class validatorClass = excelEntity.validator();
            if (source != null && !source.isEmpty()) {
                ArrayList list = new ArrayList(source.size());
                Map excelColumnFieldMapping = getExcelColumnMapping(this.tClass);
                int errorCount = 0;

                try {
                    for (int e = 0; e < source.size(); ++e) {
                        boolean allColumnRight = true;
                        Map rowData = (Map) source.get(e);
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("current row:{},data:{}", Integer.valueOf(e + 2), rowData);
                        }

                        if (this.callbackForConverter != null) {
                            this.callbackForConverter.beforeEachRow(rowData, e + 2);
                            if (!this.callbackForConverter.accecp(rowData, e + 2)) {
                                continue;
                            }
                        }

                        T instance = this.createInstance();
                        Iterator var12 = rowData.entrySet().iterator();

                        while (var12.hasNext()) {
                            Entry columnNameAndValue = (Entry) var12.next();
                            String column = null;
                            if (errorCount >= this.maxNumOfError) {
                                this.errorTemplates.add(new ErrorTemplate(e + 2, "", "已达最大错误数:[" + this.maxNumOfError + "]，停止解析！"));
                                throw new ExcelConvertException("已达最大错误数:[" + this.maxNumOfError + "]，停止解析！");
                            }

                            try {
                                column = (String) columnNameAndValue.getKey();
                                String e1 = (String) columnNameAndValue.getValue();
                                if (!excelColumnFieldMapping.containsKey(column)) {
                                    LOGGER.warn("列：[{}]，没有找到对应的映射器，忽略该列！", column);
                                } else {
                                    Field var23 = (Field) excelColumnFieldMapping.get(column);
                                    ExcelColumnMapping columnMapping = (ExcelColumnMapping) var23.getAnnotation(ExcelColumnMapping.class);
                                    boolean required = columnMapping.required();
                                    String defaultValue = columnMapping.defaultValue();
                                    if (required) {
                                        if (StringUtils.isBlank(e1)) {
                                            throw new ExcelConvertException("该列为必填项");
                                        }
                                    } else if (StringUtils.isBlank(e1)) {
                                        e1 = defaultValue;
                                    }

                                    this.tryCheck(validatorClass, rowData, e, column, e1);
                                    this.setFieldValue(instance, var23, e1);
                                }
                            } catch (ExcelConvertException var20) {
                                ++errorCount;
                                allColumnRight = false;
                                ErrorTemplate tm = new ErrorTemplate(e + 2, column, var20.getMessage());
                                this.errorTemplates.add(tm);
                            }
                        }

                        if (allColumnRight) {
                            list.add(instance);
                        } else {
                            LOGGER.error("error", this.errorTemplates);
                        }
                    }
                } catch (RuntimeException var21) {
                    throw var21;
                } catch (Exception var22) {
                    LOGGER.error(var22.getMessage(), var22);
                }

                if (this.callbackForConverter != null) {
                    this.callbackForConverter.onFinish(list, this.errorTemplates, source);
                }

                return list;
            } else {
                return Lists.newArrayList();
            }
        }
    }

    private void tryCheck(Class<? extends ExcelRuleValidator> validatorClass, Map<String, String> rowData, int row, String column, String value) throws ExcelConvertException {
        if (validatorClass != null) {
            ExcelRuleValidator ruleValidator = null;

            try {
                ruleValidator = (ExcelRuleValidator)validatorClass.newInstance();
            } catch (Exception var8) {
                throw new RuntimeException("创建验证器实例失败！");
            }

            if (this.data != null) {
                ruleValidator.setData(this.data);
            }

            Map stringStringMap = UnmodifiableMap.unmodifiableMap(rowData);
            ruleValidator.check((UnmodifiableMap)stringStringMap, row + 2, column, value);
        }

    }

    private T createInstance() throws IllegalAccessException, InstantiationException {
        T instance = this.tClass.newInstance();
        return instance;
    }

    private T setFieldValue(T instance, Field field, String value) throws ExcelConvertException {
        Class fieldType = field.getType();
        Object result = "";
        if (fieldType.isPrimitive()) {
            if (fieldType == Integer.TYPE) {
                result = Integer.valueOf((new BigDecimal(value)).intValue());
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
        } else if (fieldType == String.class) {
            result = value;
        } else if (fieldType == Date.class) {
            try {
                result = SimpleDateFormat.getDateTimeInstance().parse(value);
            } catch (ParseException var8) {
                throw new ExcelConvertException(var8.getMessage(), var8);
            }
        } else if (fieldType == BigDecimal.class) {
            result = new BigDecimal(value);
        } else if (fieldType.isArray()) {
            ;
        }

        field.setAccessible(true);

        try {
            field.set(instance, result);
            return instance;
        } catch (Exception var7) {
            throw new ExcelConvertException(var7.getMessage(), var7);
        }
    }
}
