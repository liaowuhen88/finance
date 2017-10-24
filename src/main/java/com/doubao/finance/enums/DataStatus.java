//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.doubao.finance.enums;

public enum DataStatus {
    NORMAL(1, "正常"),
    DELETED(0, "已删除");

    private int code;
    private String message;

    private DataStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
