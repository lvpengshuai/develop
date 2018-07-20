package com.trs.model;

import javax.persistence.Table;

/**
 * Created by xuxuecheng on 2017/9/18.
 */
@Table(name = "node_url")
public class NodeUrl {
    private String id;
    private String node_Url;
    private String tid;
    private Integer node_Id;
    private String name;
    private String excel_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNode_Url() {
        return node_Url;
    }

    public void setNode_Url(String node_Url) {
        this.node_Url = node_Url;
    }

    public Integer getNode_Id() {
        return node_Id;
    }

    public void setNode_Id(Integer node_Id) {
        this.node_Id = node_Id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getExcel_url() {
        return excel_url;
    }

    public void setExcel_url(String excel_url) {
        this.excel_url = excel_url;
    }
}
