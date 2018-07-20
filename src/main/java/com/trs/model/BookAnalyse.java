package com.trs.model;

/**
 * Created by zly on 2017-3-29.
 * 电子书章节标引数据
 */
public class BookAnalyse {

    private int id;
    private int bookId;
    private String bookCatalog;
    private String content;
    private String pdfpage;
    private String keyword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookCatalog() {
        return bookCatalog;
    }

    public void setBookCatalog(String bookCatalog) {
        this.bookCatalog = bookCatalog;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPdfpage() {
        return pdfpage;
    }

    public void setPdfpage(String pdfpage) {
        this.pdfpage = pdfpage;
    }
}
