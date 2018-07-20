package com.trs.model;

public class HotWord {
    private Integer id;
    private String word;
    private String gmt_create;
    private String gmt_modified;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(String gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
