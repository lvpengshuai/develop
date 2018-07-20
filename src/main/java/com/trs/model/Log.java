package com.trs.model;

import java.util.Date;

/**
 * Created by zly on 2017-3-9.
 */
public class Log {

    /*操作类型：新增*/
    public static final String LOG_OPERTYPE_INSERT = "1";
    /*操作类型：删除*/
    public static final String LOG_OPERTYPE_DELETE = "2";
    /*操作类型：修改*/
    public static final String LOG_OPERTYPE_UPDATA = "3";
    /*操作类型：查看*/
    public static final String LOG_OPERTYPE_SELECT = "4";
    /*操作类型：登录*/
    public static final String LOG_OPERTYPE_LOGIN = "5";
    /*操作类型：登出*/
    public static final String LOG_OPERTYPE_LOGOUT = "6";
//    /*操作类型：发布*/
//    public static final String LOG_OPERTYPE_PUBLISH = "7";
//    /*操作类型：撤销发布*/
//    public static final String LOG_OPERTYPE_NOPUBLISH = "8";
//    /*操作类型：上传*/
//    public static final String LOG_OPERTYPE_UPLOAD = "9";
//    /*操作类型：下载*/
//    public static final String LOG_OPERTYPE_DOWNLOAD = "10";
    /*操作类型：启用*/
    public static final String LOG_OPERTYPE_ENABLE = "7";
    /*操作类型：禁用*/
    public static final String LOG_OPERTYPE_DISABLED = "8";
//    /*审核*/
//    public static final String LOG_OPERTYPE_VERIFY = "13";
//    /*同步*/
//    public static final String LOG_OPERTYPE_SYNCHRONOUS = "14";

    /*对象类型：系统管理*/
    public static final String LOG_TARGETTYPE_SYSTEMMANAGER = "1";
    /*对象类型：知识体系管理*/
//    public static final String LOG_TARGETTYPE_ONTOLOGYINFOMANAGER = "2";
    /*对象类型：知识库管理*/
//    public static final String LOG_TARGETTYPE_KNOWLEDGEMANAGER = "3";
    /*对象类型：电子书库管理*/
//    public static final String LOG_TARGETTYPE_BOOKMANAGER = "4";
    /*对象类型：期刊库管理*/
//    public static final String LOG_TARGETTYPE_PERIODICALMANAGER = "5";
    /*对象类型：标准库管理*/
//    public static final String LOG_TARGETTYPE_STANDARDMANAGER = "6";
    /*对象类型：用户管理*/
    public static final String LOG_TARGETTYPE_USERMANAGER = "2";
    /*对象类型：会员管理*/
    public static final String LOG_TARGETTYPE_MEMBERMANAGER = "3";
    /*对象类型：机构管理*/
    public static final String LOG_TARGETTYPE_ORGANIZEMANAGER = "4";
    /*对象类型：日志管理*/
    public static final String LOG_TARGETTYPE_LogMANAGER = "5";
    /*对象类型：资源访问*/
//    public static final String LOG_TARGETTYPE_ACCESSRESOURCE = "11";


    private int id;
    /*操作时间*/
    private Date gmtCreate;
    /*操作者id*/
    private String operId;
    /*操作者名称*/
    private String operUsername;
    /*操作者ip*/
    private String ip;
    /*说明*/
    private String description;
    /*操作类型*/
    private String operType;
    /*对象类型*/
    private String targetType;
    /*是否是异常操作记录(0:是  1:否)*/
    private String isWarn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getOperUsername() {
        return operUsername;
    }

    public void setOperUsername(String operUsername) {
        this.operUsername = operUsername;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getIsWarn() {
        return isWarn;
    }

    public void setIsWarn(String isWarn) {
        this.isWarn = isWarn;
    }
}
