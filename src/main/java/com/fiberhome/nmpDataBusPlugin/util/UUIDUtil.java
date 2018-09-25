package com.fiberhome.nmpDataBusPlugin.util;

import java.util.UUID;

/**
 * @author 黃帆
 * @date 2018/9/21 11:21
 */
public class UUIDUtil {

    public static String getRandomUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
