package com.trs.model;

import com.trs.mapper.CategoryBookMapper;

/**
 * Created by xubo on 2017/4/20.
 */
public class CategoryBook {

    private String  num;
    private String name;
    private String parent;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

}
