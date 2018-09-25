package com.fiberhome.nmpDataBusPlugin.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 黃帆
 * @date 2018/9/25 11:33
 */
public class ConfigUtil {

    private static Logger logger= LogManager.getLogger(ConfigUtil.class.getName());

    private static Properties props=new Properties();

    private static final String path="config.properties";

    static {

        ClassLoader loader = ConfigUtil.class.getClassLoader();
        InputStream inputStream=null;
        try {
            inputStream=loader.getResourceAsStream(path);
            props.load(inputStream);
        } catch (IOException e) {
            logger.error(e);
        } finally{
            if(inputStream!=null){
                try {
                    inputStream.close();
                    inputStream=null;
                } catch (IOException e) {
                    logger.error(e);
                }

            }
        }
    }

    public static String getProperty(String key){
        return props.getProperty(key);
    }

    public static String getProperty(String key,String defaultValue){
        return props.getProperty(key, defaultValue);
    }
}
