package com.trs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihuan on 2017/9/19.
 */
public class UserCenterTree {
    private int id;
    private String name;
    private int fid;
    private List<UserCenterTree> childs=new ArrayList<UserCenterTree>();

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

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public List<UserCenterTree> getChilds() {
        return childs;
    }

    public void setChilds(List<UserCenterTree> childs) {
        this.childs = childs;
    }
}
