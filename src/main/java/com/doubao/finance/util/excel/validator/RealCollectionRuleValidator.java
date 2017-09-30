package com.doubao.finance.util.excel.validator;

import com.doubao.finance.util.StringUtil;
import com.doubao.finance.util.excel.exception.ExcelConvertException;
import org.apache.commons.collections4.map.UnmodifiableMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

public class RealCollectionRuleValidator
        implements ExcelRuleValidator
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RealCollectionRuleValidator.class);
    private Object data;

    public void setData(Object data)
    {
        this.data = data;
    }

    public void check(UnmodifiableMap<String, String> rowData, int row, String columnName, String columnValue)
            throws ExcelConvertException
    {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("row number:{},column:{},value:{}", new Object[] { Integer.valueOf(row), columnName, columnValue });
        }
        if (this.data != null)
        {
            Map<String, Long> chargeEntitis = (Map)this.data;
            if (StringUtils.equals("结算主体", columnName))
            {
                if (!chargeEntitis.containsKey(columnValue)) {
                    throw new ExcelConvertException("结算主体：【" + columnValue + "】不存在");
                }
                return;
            }
        }
        if ((StringUtils.equals("贷方发生额", columnName)) &&
                (rowData.containsKey("借方发生额")))
        {
            if (!StringUtil.isNumberic(columnValue)) {
                throw new ExcelConvertException("贷方发生额必须是数字");
            }
            String mm = rowData.get("借方发生额");
            if (!StringUtil.isNumberic(mm)) {
                throw new ExcelConvertException("借方发生额必须是数字");
            }
            if (((StringUtils.isEmpty(mm)) && (StringUtils.isEmpty(columnValue))) || ((StringUtils.equals("0.0", mm)) && (StringUtils.equals("0.0", columnValue)))) {
                throw new ExcelConvertException("借方发生额和贷方发生额不能同时为空");
            }
            if ((new BigDecimal(mm).compareTo(BigDecimal.ZERO) > 0) && (new BigDecimal(columnValue).compareTo(BigDecimal.ZERO) > 0)) {
                throw new ExcelConvertException("借方发生额和贷方发生额不能同时大于0");
            }
            if ((new BigDecimal(mm).compareTo(BigDecimal.ZERO) > 0) && (new BigDecimal(columnValue).compareTo(BigDecimal.ZERO) <= 0)) {}
            return;
        }
    }
}
