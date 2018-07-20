package com.trs.core.util;

import java.io.File;

/**
 * Created by root on 17-4-1.
 */
public class Status {
    public static final Status SUCCESS = new Status("10000", "成功");
    public static final Status ERROR = new Status("20000", "系统错误");
    public static final Status fileError = new Status("-2","文件格式错误");
    public static final Status emptyFileContent = new Status("-3","文件内容为空");
    public static final Status emptyFile = new Status("-4","文件为空");
    public static final String SUCCESS_CODE = "10000";
    public static final String ERROR_CODE = "20000";

    public File[] files;
    public int count;
    public int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //TODO add other error status here

    public Status() {}

    public Status(String code, Object msg) {
        this.code = code;
        this.msg = msg;
    }
    public Status(String code, Object msg,int count) {
        this.code = code;
        this.msg = msg;
        this.count=count;
    }

    private String code;
    private Object msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
