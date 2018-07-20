package com.trs.model;

/**
 * Created by 李春雨 on 2017/3/28.
 */
public class AuthorResource {
    private int id;
    private String resourceId;
    private String authorId;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AuthorResource{" +
                "id=" + id +
                ", resourceId='" + resourceId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
