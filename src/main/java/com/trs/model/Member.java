package com.trs.model;

import java.util.Date;

public class Member {

    private Integer id;

    private String username;

    private String pwd;

    private String realname;

    private Integer gender;

    private String email;

    private String telephone;

    private String address;

    private String education;

    private String major;

    private String postcode;

    private String organization;

    private Integer roleId;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer status;

    private Integer organizaId;

    private String uuCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrganizaId() {
        return organizaId;
    }

    public void setOrganizaId(Integer organizaId) {
        this.organizaId = organizaId;
    }

    public String getUuCode() {
        return uuCode;
    }

    public void setUuCode(String uuCode) {
        this.uuCode = uuCode;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", realname='" + realname + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", education='" + education + '\'' +
                ", major='" + major + '\'' +
                ", postcode='" + postcode + '\'' +
                ", organization='" + organization + '\'' +
                ", roleId=" + roleId +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", status=" + status +
                ", organizaId=" + organizaId +
                ", uuCode='" + uuCode + '\'' +
                '}';
    }
}