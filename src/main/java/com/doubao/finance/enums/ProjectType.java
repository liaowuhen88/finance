package com.doubao.finance.enums;

public enum ProjectType
{
    SERVICE_FEE(1, "服务费"),  TECHNOLOGY_SERVICE_FEE(2, "技术服务费"),  HEALTHY_SERVICE_FEE(3, "健康服务费"),  HEALTHY_CONSULT_SERVICE_FEE(4, "健康咨询服务费"),  CONSULT_SERVICE_FEE(5, "咨询服务费"),  UNKNOWN(-1, "未知发票项目类型");

    private int code;
    private String text;

    private ProjectType(int code, String text)
    {
        this.code = code;
        this.text = text;
    }

    public int getCode()
    {
        return this.code;
    }

    public String getText()
    {
        return this.text;
    }

    public static String valueOf(int code)
    {
        for (ProjectType projectType : ProjectType.values()) {
            if (projectType.getCode() == code) {
                return projectType.getText();
            }
        }
        return UNKNOWN.getText();
    }
}
