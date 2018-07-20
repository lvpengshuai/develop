package com.trs.core.util;

import java.net.URL;

/**
 * @author ThinkPad
 * @create 2017/11/2-11:16
 */
public class OS {
    public static final String CLASS_PATH;

    public static final boolean isLinux;
    static {
        URL resource = OS.class.getResource("OS.class");
        String classPath = resource.getPath();
        String className = OS.class.getName().replace('.', '/') + ".class";
        String classesPath = classPath.substring(0, classPath
                .indexOf(className));

        if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1
                && classesPath.startsWith("/")) {
            classesPath = classesPath.substring(1);
            isLinux = false;
        } else {
            isLinux = true;
        }
        CLASS_PATH = classesPath;
    }

    public static void main(String arg[]) {
        System.out.println(OS.isLinux);
    }
}
