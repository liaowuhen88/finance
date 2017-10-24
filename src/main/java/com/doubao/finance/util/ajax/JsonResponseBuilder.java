//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.doubao.finance.util.ajax;

public class JsonResponseBuilder {
    public JsonResponseBuilder() {
    }

    public static <T> JsonResponse buildJsonResponse(ResponseCode code, T data) {
        if (code == null) {
            throw new NullPointerException();
        } else {
            return data == null ? new JsonResponse(code.getCode(), code.getMessage(), "") : new JsonResponse(code.getCode(), code.getMessage(), data);
        }
    }

    public static <T> JsonResponse buildSuccessJsonResponse(T data) {
        return buildJsonResponse(ResponseCode.OK, data);
    }

    public static JsonResponse buildSuccessJsonResponseWithoutData() {
        return buildSuccessJsonResponse((Object) null);
    }

    public static <T> JsonResponse buildErrorJsonResponse(ResponseCode code, T data) {
        if (code == null) {
            throw new NullPointerException();
        } else if (code == ResponseCode.OK) {
            throw new IllegalArgumentException("build error json code ,but you passed a success json code ！");
        } else {
            return buildJsonResponse(code, data);
        }
    }

    public static JsonResponse buildErrorJsonResponseWithoutData(ResponseCode code) {
        return buildErrorJsonResponse(code, (Object) null);
    }
}
