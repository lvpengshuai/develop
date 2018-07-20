package com.trs.model;

/**
 * Created by 李春雨 on 2017/3/7.
 */
public class PeriodicalAuthor {
    /* 作者序号 */
    private int id;
    /* Periodical表中的id简历主外键关系 */
    private int indexId;
    /* 作者的姓名 */
    private String authorName;
    /* 单位地址 */
    private String company;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", indexId=" + indexId +
                ", authorName='" + authorName + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
