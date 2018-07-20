package com.trs.model;

import java.util.Date;

/**
 * Created by 李春雨 on 2017/3/3.
 */
public class Periodical {

    private int id;
    /* 期刊名称 */
    private String periodicalName;
    /* 卷 */
    private String roll;
    /* 年 */
    private String years;
    /* 月 */
    private String month;
    /* 期 */
    private String phase;
    /* 栏目 */
    private String lanmu;
    /* 中文篇名 */
    private String chName;
    /* 英文篇名 */
    private String enName;
    /* 中文摘要 */
    private String chAbstract;
    /* 英文摘要 */
    private String enAbstract;
    /* 中文关键词 */
    private String chKeyword;
    /* 英文关键词 */
    private String enKeyword;
    /* doi */
    private String doi;
    /* 中图分类号 */
    private String chCategoryId;
    /* 文献标志码 */
    private String docsFlagCode;
    /* 文章编号 */
    private String articleId;
    /* 基金项目 */
    private String fundProgram;
    /* 基金项目编号 */
    private String fundProgramId;
    /* 资源标识 */
    private String state;
    /* 发布时间 */
    private Date pubDate;
    /* 创建资源时间 */
    private Date gmtCreate;
    /* 修改资源时间 */
    private Date gmtModified;
    /* pdf文件大小 */
    private int PDFWenJianDaXiao;
    /* pdf文件是否本地 */
    private String PDFShiFouBenDi;
    /* pdf链接地址 */
    private String PDFLianJieDiZhi;
    /* PDF_WenJian pdf文件名称 */
    private String PDFWenJian;
    /* pdf想对路径 */
    private String PDFUrl;
    /* pdf下载次数 */
    private int PDF_XiaZaiCiShu;
    /* 全文 */
    private String quanwen;
    /* 录入人员 */
    private String lururenyuan;

    /* 通讯作者 */
    private String lianXiZuoZhe;

    /* 作者单位 */
    private String zuoZheDanWei;

    /* 参考文献 */
    private String canKaoWenXian;
    /* 文献文件*/
    private String wenXianWenJian;
    /* 杂志id */
    private String zaZhiId;

    /* 年卷期id */
    private String nianJuanQiId;

    /* 不含标签的摘要 */
    private String newChAbstract;

    /* 不含标签的文章中文名称 */
    private String noTagsChName;

    /* 不含标签的文章英文名称 */
    private String noTagsEnName;

    /* 投稿时间 */
    private Date gmtContribute;

    /* 作者简介 */
    private String authorAbstract;

    /* 作者email */
    private String authorEmail;

    /* 不含标签的关键词 */
    private String noTagsChKeyword;

    /* 通讯作者 */
    private String authorName;

    /* 作者英文单位 */
    private String enAuthorCompany;

    /* 通讯作者简介 */
    private String tongxunzuozheAbstract;

    /* 起始页 */
    private String startPage;

    /* 结束页 */
    private String endPage;


    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    public String getEndPage() {
        return endPage;
    }

    public void setEndPage(String endPage) {
        this.endPage = endPage;
    }

    public String getTongxunzuozheAbstract() {
        return tongxunzuozheAbstract;
    }

    public void setTongxunzuozheAbstract(String tongxunzuozheAbstract) {
        this.tongxunzuozheAbstract = tongxunzuozheAbstract;
    }

    public String getEnAuthorCompany() {
        return enAuthorCompany;
    }

    public void setEnAuthorCompany(String enAuthorCompany) {
        this.enAuthorCompany = enAuthorCompany;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getZuoZheDanWei() {
        return zuoZheDanWei;
    }

    public void setZuoZheDanWei(String zuoZheDanWei) {
        this.zuoZheDanWei = zuoZheDanWei;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getNoTagsChName() {
        return noTagsChName;
    }

    public void setNoTagsChName(String noTagsChName) {
        this.noTagsChName = noTagsChName;
    }

    public String getNoTagsEnName() {
        return noTagsEnName;
    }

    public void setNoTagsEnName(String noTagsEnName) {
        this.noTagsEnName = noTagsEnName;
    }

    public Date getGmtContribute() {
        return gmtContribute;
    }

    public void setGmtContribute(Date gmtContribute) {
        this.gmtContribute = gmtContribute;
    }

    public String getAuthorAbstract() {
        return authorAbstract;
    }

    public void setAuthorAbstract(String authorAbstract) {
        this.authorAbstract = authorAbstract;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getNoTagsChKeyword() {
        return noTagsChKeyword;
    }

    public void setNoTagsChKeyword(String noTagsChKeyword) {
        this.noTagsChKeyword = noTagsChKeyword;
    }

    public String getNianJuanQiId() {
        return nianJuanQiId;
    }

    public void setNianJuanQiId(String nianJuanQiId) {
        this.nianJuanQiId = nianJuanQiId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeriodicalName() {
        return periodicalName;
    }

    public void setPeriodicalName(String periodicalName) {
        this.periodicalName = periodicalName;
    }


    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getLanmu() {
        return lanmu;
    }

    public void setLanmu(String lanmu) {
        this.lanmu = lanmu;
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

    public String getChAbstract() {
        return chAbstract;
    }

    public void setChAbstract(String chAbstract) {
        this.chAbstract = chAbstract;
    }

    public String getEnAbstract() {
        return enAbstract;
    }

    public void setEnAbstract(String enAbstract) {
        this.enAbstract = enAbstract;
    }

    public String getChKeyword() {
        return chKeyword;
    }

    public void setChKeyword(String chKeyword) {
        this.chKeyword = chKeyword;
    }

    public String getEnKeyword() {
        return enKeyword;
    }

    public void setEnKeyword(String enKeyword) {
        this.enKeyword = enKeyword;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getChCategoryId() {
        return chCategoryId;
    }

    public void setChCategoryId(String chCategoryId) {
        this.chCategoryId = chCategoryId;
    }

    public String getDocsFlagCode() {
        return docsFlagCode;
    }

    public void setDocsFlagCode(String docsFlagCode) {
        this.docsFlagCode = docsFlagCode;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getFundProgram() {
        return fundProgram;
    }

    public void setFundProgram(String fundProgram) {
        this.fundProgram = fundProgram;
    }

    public String getFundProgramId() {
        return fundProgramId;
    }

    public void setFundProgramId(String fundProgramId) {
        this.fundProgramId = fundProgramId;
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

    public int getPDFWenJianDaXiao() {
        return PDFWenJianDaXiao;
    }

    public void setPDFWenJianDaXiao(int PDFWenJianDaXiao) {
        this.PDFWenJianDaXiao = PDFWenJianDaXiao;
    }

    public String getPDFShiFouBenDi() {
        return PDFShiFouBenDi;
    }

    public void setPDFShiFouBenDi(String PDFShiFouBenDi) {
        this.PDFShiFouBenDi = PDFShiFouBenDi;
    }

    public String getPDFLianJieDiZhi() {
        return PDFLianJieDiZhi;
    }

    public void setPDFLianJieDiZhi(String PDFLianJieDiZhi) {
        this.PDFLianJieDiZhi = PDFLianJieDiZhi;
    }

    public String getPDFWenJian() {
        return PDFWenJian;
    }

    public void setPDFWenJian(String PDFWenJian) {
        this.PDFWenJian = PDFWenJian;
    }

    public String getPDFUrl() {
        return PDFUrl;
    }

    public void setPDFUrl(String PDFUrl) {
        this.PDFUrl = PDFUrl;
    }

    public int getPDF_XiaZaiCiShu() {
        return PDF_XiaZaiCiShu;
    }

    public void setPDF_XiaZaiCiShu(int PDF_XiaZaiCiShu) {
        this.PDF_XiaZaiCiShu = PDF_XiaZaiCiShu;
    }

    public String getQuanwen() {
        return quanwen;
    }

    public void setQuanwen(String quanwen) {
        this.quanwen = quanwen;
    }

    public String getLururenyuan() {
        return lururenyuan;
    }

    public void setLururenyuan(String lururenyuan) {
        this.lururenyuan = lururenyuan;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getLianXiZuoZhe() {
        return lianXiZuoZhe;
    }

    public void setLianXiZuoZhe(String lianXiZuoZhe) {
        this.lianXiZuoZhe = lianXiZuoZhe;
    }

    public String getCanKaoWenXian() {
        return canKaoWenXian;
    }

    public void setCanKaoWenXian(String canKaoWenXian) {
        this.canKaoWenXian = canKaoWenXian;
    }

    public String getWenXianWenJian() {
        return wenXianWenJian;
    }

    public void setWenXianWenJian(String wenXianWenJian) {
        this.wenXianWenJian = wenXianWenJian;
    }

    public String getZaZhiId() {
        return zaZhiId;
    }

    public void setZaZhiId(String zaZhiId) {
        this.zaZhiId = zaZhiId;
    }

    public String getNewChAbstract() {
        return newChAbstract;
    }

    public void setNewChAbstract(String newChAbstract) {
        this.newChAbstract = newChAbstract;
    }

    @Override
    public String toString() {
        return "Periodical{" +
                "id=" + id +
                ", periodicalName='" + periodicalName + '\'' +
                ", roll='" + roll + '\'' +
                ", years='" + years + '\'' +
                ", month='" + month + '\'' +
                ", phase='" + phase + '\'' +
                ", lanmu='" + lanmu + '\'' +
                ", chName='" + chName + '\'' +
                ", enName='" + enName + '\'' +
                ", chAbstract='" + chAbstract + '\'' +
                ", enAbstract='" + enAbstract + '\'' +
                ", chKeyword='" + chKeyword + '\'' +
                ", enKeyword='" + enKeyword + '\'' +
                ", doi='" + doi + '\'' +
                ", chCategoryId='" + chCategoryId + '\'' +
                ", docsFlagCode='" + docsFlagCode + '\'' +
                ", articleId='" + articleId + '\'' +
                ", fundProgram='" + fundProgram + '\'' +
                ", fundProgramId='" + fundProgramId + '\'' +
                ", state='" + state + '\'' +
                ", pubDate=" + pubDate +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", PDFWenJianDaXiao=" + PDFWenJianDaXiao +
                ", PDFShiFouBenDi='" + PDFShiFouBenDi + '\'' +
                ", PDFLianJieDiZhi='" + PDFLianJieDiZhi + '\'' +
                ", PDFWenJian='" + PDFWenJian + '\'' +
                ", PDFUrl='" + PDFUrl + '\'' +
                ", PDF_XiaZaiCiShu=" + PDF_XiaZaiCiShu +
                ", quanwen='" + quanwen + '\'' +
                ", lururenyuan='" + lururenyuan + '\'' +
                ", lianXiZuoZhe='" + lianXiZuoZhe + '\'' +
                ", canKaoWenXian='" + canKaoWenXian + '\'' +
                ", wenXianWenJian='" + wenXianWenJian + '\'' +
                ", zaZhiId='" + zaZhiId + '\'' +
                ", newChAbstract='" + newChAbstract + '\'' +
                '}';
    }
}
