package com.trs.model;
/**
 * Created by xuxuecheng on 2017-8-24.
 *
 */
import javax.persistence.*;
import java.util.Date;

@Table(name = "book_details")
public class BookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String zid;

    private String fid;

    private String title;

    @Column(name = "content_pdf")
    private String contentPdf;

    private String entry;

    private String source;

    private String bookcode;

    @Column(name = "zid_title")
    private String zidTitle;

    private String keyword;

    private String pdfpage;

    private String bookpage;

    @Column(name = "readCount")
    private Integer readCount;

    private String classify;

    private String people;

    private String organ;

    private String bookname;

    private String bookyear;

    private String depth;

    private String hot;

    private Date exdate;

    private String exarea;

    private String exdata;

    @Column(name = "TextContent")
    private String textContent;

    @Column(name = "HtmlContent")
    private String htmlContent;

    private String abs;




    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return zid
     */
    public String getZid() {
        return zid;
    }

    /**
     * @param zid
     */
    public void setZid(String zid) {
        this.zid = zid;
    }

    /**
     * @return fid
     */
    public String getFid() {
        return fid;
    }

    /**
     * @param fid
     */
    public void setFid(String fid) {
        this.fid = fid;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return content_pdf
     */
    public String getContentPdf() {
        return contentPdf;
    }

    /**
     * @param contentPdf
     */
    public void setContentPdf(String contentPdf) {
        this.contentPdf = contentPdf;
    }

    /**
     * @return entry
     */
    public String getEntry() {
        return entry;
    }

    /**
     * @param entry
     */
    public void setEntry(String entry) {
        this.entry = entry;
    }

    /**
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return bookcode
     */
    public String getBookcode() {
        return bookcode;
    }

    /**
     * @param bookcode
     */
    public void setBookcode(String bookcode) {
        this.bookcode = bookcode;
    }

    /**
     * @return zid_title
     */
    public String getZidTitle() {
        return zidTitle;
    }

    /**
     * @param zidTitle
     */
    public void setZidTitle(String zidTitle) {
        this.zidTitle = zidTitle;
    }

    /**
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return pdfpage
     */
    public String getPdfpage() {
        return pdfpage;
    }

    /**
     * @param pdfpage
     */
    public void setPdfpage(String pdfpage) {
        this.pdfpage = pdfpage;
    }

    /**
     * @return bookpage
     */
    public String getBookpage() {
        return bookpage;
    }

    /**
     * @param bookpage
     */
    public void setBookpage(String bookpage) {
        this.bookpage = bookpage;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    /**
     * @return classify
     */
    public String getClassify() {
        return classify;
    }

    /**
     * @param classify
     */
    public void setClassify(String classify) {
        this.classify = classify;
    }

    /**
     * @return people
     */
    public String getPeople() {
        return people;
    }

    /**
     * @param people
     */
    public void setPeople(String people) {
        this.people = people;
    }

    /**
     * @return organ
     */
    public String getOrgan() {
        return organ;
    }

    /**
     * @param organ
     */
    public void setOrgan(String organ) {
        this.organ = organ;
    }

    /**
     * @return bookname
     */
    public String getBookname() {
        return bookname;
    }

    /**
     * @param bookname
     */
    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    /**
     * @return bookyear
     */
    public String getBookyear() {
        return bookyear;
    }

    /**
     * @param bookyear
     */
    public void setBookyear(String bookyear) {
        this.bookyear = bookyear;
    }

    /**
     * @return depth
     */
    public String getDepth() {
        return depth;
    }

    /**
     * @param depth
     */
    public void setDepth(String depth) {
        this.depth = depth;
    }

    /**
     * @return hot
     */
    public String getHot() {
        return hot;
    }

    /**
     * @param hot
     */
    public void setHot(String hot) {
        this.hot = hot;
    }

    /**
     * @return exdate
     */
    public Date getExdate() {
        return exdate;
    }

    /**
     * @param exdate
     */
    public void setExdate(Date exdate) {
        this.exdate = exdate;
    }

    /**
     * @return exarea
     */
    public String getExarea() {
        return exarea;
    }

    /**
     * @param exarea
     */
    public void setExarea(String exarea) {
        this.exarea = exarea;
    }

    /**
     * @return exdata
     */
    public String getExdata() {
        return exdata;
    }

    /**
     * @param exdata
     */
    public void setExdata(String exdata) {
        this.exdata = exdata;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    /**
     * @return abs
     */
    public String getAbs() {
        return abs;
    }

    /**
     * @param abs
     */
    public void setAbs(String abs) {
        this.abs = abs;
    }

    @Override
    public String toString() {
        return "BookDetails{" +
                "id=" + id +
                ", zid='" + zid + '\'' +
                ", fid='" + fid + '\'' +
                ", title='" + title + '\'' +
                ", contentPdf='" + contentPdf + '\'' +
                ", entry='" + entry + '\'' +
                ", source='" + source + '\'' +
                ", bookcode='" + bookcode + '\'' +
                ", zidTitle='" + zidTitle + '\'' +
                ", keyword='" + keyword + '\'' +
                ", pdfpage='" + pdfpage + '\'' +
                ", bookpage='" + bookpage + '\'' +
                ", readCount=" + readCount +
                ", classify='" + classify + '\'' +
                ", people='" + people + '\'' +
                ", organ='" + organ + '\'' +
                ", bookname='" + bookname + '\'' +
                ", bookyear='" + bookyear + '\'' +
                ", depth='" + depth + '\'' +
                ", hot='" + hot + '\'' +
                ", exdate=" + exdate +
                ", exarea='" + exarea + '\'' +
                ", exdata='" + exdata + '\'' +
                ", textContent='" + textContent + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", abs='" + abs + '\'' +
                '}';
    }
}
