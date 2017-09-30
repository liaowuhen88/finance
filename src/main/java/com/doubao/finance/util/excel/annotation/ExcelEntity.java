package com.doubao.finance.util.excel.annotation;

import com.doubao.finance.util.excel.validator.DefaultRule;
import com.doubao.finance.util.excel.validator.ExcelRuleValidator;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelEntity
{
    int maxNumOfError() default Integer.MAX_VALUE;

    Class<? extends ExcelRuleValidator> validator() default DefaultRule.class;
}
