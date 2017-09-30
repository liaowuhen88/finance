package com.doubao.finance.util;

import com.google.gson.Gson;
import java.lang.reflect.Type;

public class JSONUtil
{
    public static String toJson(Object o)
    {
        Gson gson = new Gson();

        return gson.toJson(o);
    }

    public static <T> T toObject(Class<T> c, String json)
            throws Exception
    {
        Gson gson = new Gson();

        T msg = gson.fromJson(json, c);

        return msg;
    }

    public static <T> T fromJson(String json, Type typeOfT)
    {
        Gson gson = new Gson();
        T msg = gson.fromJson(json, typeOfT);

        return msg;
    }
}
