package com.trs.model;

import java.util.List;

public class BookClassify {

    private int id;
    private String bookabbreviate;
    private String name;
    private String image;
    private int order;
    private String mendtime;
    private int counts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookabbreviate() {
        return bookabbreviate;
    }

    public void setBookabbreviate(String bookabbreviate) {
        this.bookabbreviate = bookabbreviate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getMendtime() {
        return mendtime;
    }

    public void setMendtime(String mendtime) {
        this.mendtime = mendtime;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    @Override
    public String toString() {
        return "BookClassify{" +
                "id=" + id +
                ", bookabbreviate='" + bookabbreviate + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", order=" + order +
                ", mendtime='" + mendtime + '\'' +
                ", count=" + counts +
                '}';
    }

}

