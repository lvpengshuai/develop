package com.trs.model;

import javax.persistence.Table;

/**
 * Created by xuxuecheng on 2017/9/7.
 */
@Table(name = "node_data")
public class NodeData {

    private int id;
    private int fid;
    private String name;
    private int state;
    private String nodeurl;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeurl() {
        return nodeurl;
    }

    public void setNodeurl(String nodeurl) {
        this.nodeurl = nodeurl;
    }

    @Override
    public String toString() {
        return "NodeData{" +
                "id=" + id +
                ", fid=" + fid +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", nodeurl='" + nodeurl + '\'' +
                '}';
    }
}
