//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.doubao.finance.util.ajax;

public enum ResponseCode {
    OK(1000, "OK"),
    ERROR_PARAMETER(2000, "参数缺失或非法"),
    ERROR_ENTITY_NOT_EXISTS(3000, "数据不存在"),
    ERROR_SERVER_ERROR(4000, "系统内部错误"),
    ERROR_UPLOAD_FILE_FORMAT(5000, "上传的文件类型格式不正确"),
    ERROR_UPLOAD_FILE_SIZE_TOO_LARGE(5001, "上传的文件不能超过10M"),
    ERROR_USER_NOT_LOGIN(6000, "用户需要登录");

    private int code;
    private String message;

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
