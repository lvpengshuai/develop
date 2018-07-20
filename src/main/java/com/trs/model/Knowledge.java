package com.trs.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Knowledge {
    private Integer id;

    private String knowledgeName;

    private String englishName;

    private String parentClass;

    private Date createTime;

    private Date lastSynTime;

    private Integer status;

    private String property;

    public Knowledge(Integer id, String knowledgeName, String englishName, String parentClass, Date createTime, Date lastSynTime, Integer status, String property) {
        this.id = id;
        this.knowledgeName = knowledgeName;
        this.englishName = englishName;
        this.parentClass = parentClass;
        this.createTime = createTime;
        this.lastSynTime = lastSynTime;
        this.status = status;
        this.property = property;
    }

    public Knowledge() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName == null ? null : knowledgeName.trim();
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    public String getParentClass() {
        return parentClass;
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass == null ? null : parentClass.trim();
    }

    public String getCreateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastSynTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(lastSynTime);
    }

    public void setLastSynTime(Date lastSynTime) {
        this.lastSynTime = lastSynTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }
}