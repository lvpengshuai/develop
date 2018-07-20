package com.trs.model;

import java.io.Serializable;

/**
 * Created by xuxuecheng on 2017/9/12.
 */
public class NodeMenu implements Serializable {
    private String id;
    private String name;//树节点名称
    private Integer pId;
    private String myAttr;

    public String getMyAttr() {
        return myAttr;
    }

    public void setMyAttr(String myAttr) {
        this.myAttr = myAttr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
