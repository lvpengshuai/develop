package com.trs.model;

import java.util.Date;

/**
 * Created by zly on 2017-5-11.
 */
public class Organize {

    private Integer id;
    /*组织名称*/
    private String name;
    /*地址*/
    private String address;
    /*电话号码*/
    private String telephone;
    /*有效日期*/
    private Date expiration;
    /*权限id*/
    private int roleId;
    /*创建时间*/
    private Date gmtCreate;
    /*修改时间*/
    private Date gmtModified;
    /*状态*/
    private Integer status;
    /*ip开始*/
    private String ipStart;
    /*ip结束*/
    private String ipEnd;
    /*最大并发数量*/
    private int maxOnline;
    /*附件*/
    private String file;
    /*zas接口code*/
    private String code;
    /*zas接口机构映射*/
    private String bid;

    private String orgName;

    private String ipStartOne;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
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

    public String getIpStart() {
        return ipStart;
    }

    public void setIpStart(String ipStart) {
        this.ipStart = ipStart;
    }

    public String getIpEnd() {
        return ipEnd;
    }

    public void setIpEnd(String ipEnd) {
        this.ipEnd = ipEnd;
    }

    public int getMaxOnline() {
        return maxOnline;
    }

    public void setMaxOnline(int maxOnline) {
        this.maxOnline = maxOnline;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getIpStartOne() {
        return ipStartOne;
    }

    public void setIpStartOne(String ipStartOne) {
        this.ipStartOne = ipStartOne;
    }

    @Override
    public String toString() {
        return "Organize{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", expiration=" + expiration +
                ", roleId=" + roleId +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", status=" + status +
                ", ipStart='" + ipStart + '\'' +
                ", ipEnd='" + ipEnd + '\'' +
                ", maxOnline=" + maxOnline +
                ", file='" + file + '\'' +
                ", code='" + code + '\'' +
                ", bid='" + bid + '\'' +
                ", orgName='" + orgName + '\'' +
                ", ipStartOne='" + ipStartOne + '\'' +
                '}';
    }
}
