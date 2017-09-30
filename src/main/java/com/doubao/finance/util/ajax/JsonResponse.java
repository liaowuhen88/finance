package com.doubao.finance.util.ajax;

import com.doubao.finance.util.BaseModel;

public class JsonResponse<T>
        extends BaseModel
{
    private int code;
    private String message;
    private boolean success;
    private T data;

    public JsonResponse() {}

    public JsonResponse(int code, String message, T data)
    {
        this.code = code;
        this.message = message;
        this.data = data;
        setSuccess(this.code);
    }

    public int getCode()
    {
        return this.code;
    }

    public void setCode(int code)
    {
        this.code = code;
        setSuccess(this.code);
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public T getData()
    {
        return (T)this.data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public boolean isSuccess()
    {
        return this.success;
    }

    private void setSuccess(int code)
    {
        this.success = (code == ResponseCode.OK.getCode());
    }
}
