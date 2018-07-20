package com.trs.model;

/**
 * Created by 李春雨 on 2017/3/28.
 */
public class TmpAuthor {

    private int id;
    private String name;
    private String organization;
    private int ZaZhiId;
    private int WenZhangId;


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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getZaZhiId() {
        return ZaZhiId;
    }

    public void setZaZhiId(int zaZhiId) {
        ZaZhiId = zaZhiId;
    }

    public int getWenZhangId() {
        return WenZhangId;
    }

    public void setWenZhangId(int wenZhangId) {
        WenZhangId = wenZhangId;
    }

    @Override
    public String toString() {
        return "TmpAuthor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                ", ZaZhiId=" + ZaZhiId +
                ", WenZhangId=" + WenZhangId +
                '}';
    }
}
