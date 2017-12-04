package com.gittoy.utils;

import java.util.Random;

/**
 * KeyUtil
 * Create By GaoYu 2017/11/15 14:29
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     *
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        // 生成六位随机数
        Integer randomNumber = random.nextInt(900000) + 100000;
        return  System.currentTimeMillis() + String.valueOf(randomNumber);
    }
}
