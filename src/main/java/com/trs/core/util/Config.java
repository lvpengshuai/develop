package com.trs.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;

/**
 * Created by zly on 2017-3-13.
 */
public class Config {

    public static Properties properties = new Properties();
    public static JSONArray jsonarry = new JSONArray();

    static {
        reload();
    }

    public static void reload() {
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("%20", " ")
                    + "db.properties";
            properties.load(new FileInputStream(path));

            if(null == properties) {
                System.out.println("Can't load Config.properties, please check in the path at Config.java.");
            }

            InputStream inputStream1 = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("%20", " ")
                    + "catalog.json");
            String str = IOUtils.toString(inputStream1, "utf8");
            jsonarry = JSON.parseArray(str);

        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public static String getKey(String name) {
        return properties.getProperty(name);
    }

}
