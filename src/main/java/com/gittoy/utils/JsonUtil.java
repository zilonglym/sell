package com.gittoy.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JsonUtil
 * Create By GaoYu 2017/11/21 13:40
 */
public class JsonUtil {

    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
