package com.trs.model;

/**
 * Created by lcy on 2017/4/14.
 */
public class CategoryStandard {
    private int id;
    private String num;
    private String name;
    private String remark;
    private String serial;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "StandardCategory{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", serial='" + serial + '\'' +
                '}';
    }
}
