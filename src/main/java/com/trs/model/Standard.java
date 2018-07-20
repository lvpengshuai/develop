package com.trs.model;

import java.util.Date;

/**
 * Created by 李春雨 on 2017/3/6.
 */
public class Standard {

    private int id;
    /*书号*/
    private String bookId;
    /*标准编号*/
    private String standardId;
    /*中文名称*/
    private String chName;
    /*英文名称*/
    private String enName;
    /*国际标准分类号*/
    private String internationalStandardCategoryId;
    /*中国标准分类号*/
    private String chStandardCategoryId;
    /*国别*/
    private String country;
    /*标准类型*/
    private String standardCategory;
    /*实施日期*/
    private Date executeDate;
    /*发布单位*/
    private String pushCompany;
    /*提出单位*/
    private String presentCompany;
    /*归口单位*/
    private String putCompany;
    /*起草单位*/
    private String draftCompany;
    /*起草人*/
    private String drafter;
    /*发行出版*/
    private String press;
    /*定价*/
    private String pricing;
    /*简介*/
    private String intro;
    /*标准现状（现行/即将实施/作废/废止）*/
    private String standardState;
    /*替代情况*/
    private String replaceState;
    /*页数*/
    private String pages;
    /*状态标识*/
    private String state;
    /* 发布日期 */
    private Date pubDate;
    /* 创建日期 */
    private Date gmtCreate;
    /* 修改日期 */
    private Date gmtModified;
    /* pdfUrl*/
    private String pdfUrl;
    /* pdfWenJian*/
    private String pdfWenJian;
    /* keyword */
    private String keyWord;
    /* 标准状态（新） */
    private String standardNewState;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getInternationalStandardCategoryId() {
        return internationalStandardCategoryId;
    }

    public void setInternationalStandardCategoryId(String internationalStandardCategoryId) {
        this.internationalStandardCategoryId = internationalStandardCategoryId;
    }

    public String getChStandardCategoryId() {
        return chStandardCategoryId;
    }

    public void setChStandardCategoryId(String chStandardCategoryId) {
        this.chStandardCategoryId = chStandardCategoryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStandardCategory() {
        return standardCategory;
    }

    public void setStandardCategory(String standardCategory) {
        this.standardCategory = standardCategory;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

    public String getPushCompany() {
        return pushCompany;
    }

    public void setPushCompany(String pushCompany) {
        this.pushCompany = pushCompany;
    }

    public String getPresentCompany() {
        return presentCompany;
    }

    public void setPresentCompany(String presentCompany) {
        this.presentCompany = presentCompany;
    }

    public String getPutCompany() {
        return putCompany;
    }

    public void setPutCompany(String putCompany) {
        this.putCompany = putCompany;
    }

    public String getDraftCompany() {
        return draftCompany;
    }

    public void setDraftCompany(String draftCompany) {
        this.draftCompany = draftCompany;
    }

    public String getDrafter() {
        return drafter;
    }

    public void setDrafter(String drafter) {
        this.drafter = drafter;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getStandardState() {
        return standardState;
    }

    public void setStandardState(String standardState) {
        this.standardState = standardState;
    }

    public String getReplaceState() {
        return replaceState;
    }

    public void setReplaceState(String replaceState) {
        this.replaceState = replaceState;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
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

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfWenJian() {
        return pdfWenJian;
    }

    public void setPdfWenJian(String pdfWenJian) {
        this.pdfWenJian = pdfWenJian;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public String toString() {
        return "Standard{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", standardId='" + standardId + '\'' +
                ", chName='" + chName + '\'' +
                ", enName='" + enName + '\'' +
                ", internationalStandardCategoryId='" + internationalStandardCategoryId + '\'' +
                ", chStandardCategoryId='" + chStandardCategoryId + '\'' +
                ", country='" + country + '\'' +
                ", standardCategory='" + standardCategory + '\'' +
                ", executeDate=" + executeDate +
                ", pushCompany='" + pushCompany + '\'' +
                ", presentCompany='" + presentCompany + '\'' +
                ", putCompany='" + putCompany + '\'' +
                ", draftCompany='" + draftCompany + '\'' +
                ", drafter='" + drafter + '\'' +
                ", press='" + press + '\'' +
                ", pricing='" + pricing + '\'' +
                ", intro='" + intro + '\'' +
                ", standardState='" + standardState + '\'' +
                ", replaceState='" + replaceState + '\'' +
                ", pages='" + pages + '\'' +
                ", state='" + state + '\'' +
                ", pubDate=" + pubDate +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", pdfWenJian='" + pdfWenJian + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", standardNewState='" + standardNewState + '\'' +
                '}';
    }

    public String getStandardNewState() {
        return standardNewState;
    }

    public void setStandardNewState(String standardNewState) {
        this.standardNewState = standardNewState;
    }
}
