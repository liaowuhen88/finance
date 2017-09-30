package com.doubao.finance.service.impl;

import com.doubao.finance.dao.SerialNumberDao;
import com.doubao.finance.dao.SerialNumberRuleDao;
import com.doubao.finance.pojo.SerialNumber;
import com.doubao.finance.pojo.SerialNumberRule;
import com.doubao.finance.service.SerialNumberService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SerialNumberServiceImpl
        implements SerialNumberService {
    @Autowired
    private SerialNumberRuleDao serialNumberRuleDao;
    @Autowired
    private SerialNumberDao serialNumberDao;
    private SerialNumberRule serialNumberRule;

    public SerialNumberRule saveSerialNumberRule(SerialNumberRule rule) {
        if ((rule != null) &&
                (StringUtils.isNotBlank(rule.getBusinessName()))) {
            SerialNumberRule serialNumberRule = this.serialNumberRuleDao.queryByBusinessName(rule.getBusinessName());
            if (serialNumberRule != null) {
                throw new UnsupportedOperationException("business with name " + rule.getBusinessName() + " is alreay exists!");
            }
            this.serialNumberRuleDao.insertSerialNumberRule(rule);
        }
        return rule;
    }

    public synchronized String generateSerialNumber(String businessName) {
        this.serialNumberRule = this.serialNumberRuleDao.queryByBusinessName(businessName);
        long start = this.serialNumberRule.getStart();
        int step = this.serialNumberRule.getStep();
        byte width = this.serialNumberRule.getWidth();

        String today = DateFormatUtils.format(System.currentTimeMillis(), this.serialNumberRule.getDateFormat());
        StringBuffer stringBuffer = new StringBuffer(20);

        SerialNumber number = isNewDate(businessName);
        long thisNumber = start;
        if (number == null) {
            this.serialNumberDao.insertSerialNumber(new SerialNumber(businessName, today, thisNumber));
        } else {
            thisNumber = number.getCurVal() + step;
            number.setCurVal(thisNumber);
            this.serialNumberDao.updateSerialNumber(number);
        }
        stringBuffer.append(this.serialNumberRule.getPrefix()).append(today);

        int len = String.valueOf(thisNumber).length();
        if (width >= len) {
            for (int i = 0; i < width - len; i++) {
                stringBuffer.append(String.valueOf("0"));
            }
        }
        stringBuffer.append(String.valueOf(thisNumber));
        return stringBuffer.toString();
    }

    private SerialNumber isNewDate(String businessName) {
        String today = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
        SerialNumber serialNumber = this.serialNumberDao.queryByBusinessNameAndDay(businessName, today);
        return serialNumber;
    }
}
