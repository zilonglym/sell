package com.gittoy.utils;

import com.gittoy.enums.CodeEnum;

/**
 * EnumUtil
 * Create By GaoYu 2017/11/22 12:34
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {

        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
