package com.doubao.finance.util;

import java.io.PrintStream;
import org.apache.commons.lang3.StringUtils;

public class StringUtil
{
    public static boolean isNumberic(String str)
    {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        int index = str.indexOf(".");
        if (index <= 0) {
            return StringUtils.isNumeric(str);
        }
        String[] split = str.split("\\.");
        if (split.length > 2) {
            return false;
        }
        return (StringUtils.isNumeric(split[0])) && (StringUtils.isNumeric(split[1]));
    }

    public static void main(String[] args)
    {
        System.out.println(isNumberic("1.23"));
        System.out.println(isNumberic("1.2.3"));
        System.out.println(isNumberic("null"));
        System.out.println(isNumberic(null));
        System.out.println(isNumberic(""));
        System.out.println(isNumberic(" "));
        System.out.println(isNumberic("  "));
    }
}
