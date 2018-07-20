package com.trs.model;

public class BookFile {
    private Integer id;
    private String bookcode;
    private String zid;
    private String fid;
    private String title;
    private String content;
    private String fileurl;
    private String filetype;
    private String bookpage;
    private String pdfpage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookcode() {
        return bookcode;
    }

    public void setBookcode(String bookcode) {
        this.bookcode = bookcode;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getBookpage() {
        return bookpage;
    }

    public void setBookpage(String bookpage) {
        this.bookpage = bookpage;
    }

    public String getPdfpage() {
        return pdfpage;
    }

    public void setPdfpage(String pdfpage) {
        this.pdfpage = pdfpage;
    }
}
