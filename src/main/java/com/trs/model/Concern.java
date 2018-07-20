package com.trs.model;

import java.util.Date;

/**
 * Created by epro on 2017/8/24.
 */
public class Concern {
    private int id;
    /*关注的名字*/
    private String name;
    /*关注得时间*/
    private Date gmtCreate  ;
    /*关注人的用户名*/
    private String userName;
    /*关注人的ID*/
    private String nameId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }
}
