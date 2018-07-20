package com.trs.model;

import java.util.List;

/**
 * Created by lihuan on 2017-8-28.
 */
public class Book {

    private int id;
    /*图书ID*/
    private String title;

    private String bookname;
    /*图书标题*/
    private String title_en;
    /**/
    private String bookYear;
    /*出版年份*/
    private String pubdate;

    private String author;

    private String publisherName;

    private String isbn;

    private String issn;

    private String bookType;

    private String bookAbbreviate;

    private String classification;

    private String charCount;

    private String abs;

    private String cover;

    private String bookcode;

    private String people;

    private String organ;

    private String hot;

    private String keyword;

    private String bookClass;

    private String hottype;

    private String mainmsg;

    private String epub;

    private int yearListSize;


    public int getYearListSize() {
        return yearListSize;
    }

    public void setYearListSize(int yearListSize) {
        this.yearListSize = yearListSize;
    }

    public String getEpub() {
        return epub;
    }

    public void setEpub(String epub) {
        this.epub = epub;
    }

    private List<BookYears> bookYearsList;

    public String getHottype() {
        return hottype;
    }

    public void setHottype(String hottype) {
        this.hottype = hottype;
    }

    public List<BookYears> getBookYearsList() {
        return bookYearsList;
    }

    public void setBookYearsList(List<BookYears> bookYearsList) {
        this.bookYearsList = bookYearsList;
    }

    public String getBookClass() {
        return bookClass;
    }

    public void setBookClass(String bookClass) {
        this.bookClass = bookClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getBookYear() {
        return bookYear;
    }

    public void setBookYear(String bookYear) {
        this.bookYear = bookYear;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookAbbreviate() {
        return bookAbbreviate;
    }

    public void setBookAbbreviate(String bookAbbreviate) {
        this.bookAbbreviate = bookAbbreviate;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getCharCount() {
        return charCount;
    }

    public void setCharCount(String charCount) {
        this.charCount = charCount;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBookcode() {
        return bookcode;
    }

    public void setBookcode(String bookcode) {
        this.bookcode = bookcode;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMainmsg() {
        return mainmsg;
    }

    public void setMainmsg(String mainmsg) {
        this.mainmsg = mainmsg;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", bookname='" + bookname + '\'' +
                ", title_en='" + title_en + '\'' +
                ", bookYear='" + bookYear + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", author='" + author + '\'' +
                ", publisherName='" + publisherName + '\'' +
                ", isbn='" + isbn + '\'' +
                ", issn='" + issn + '\'' +
                ", bookType='" + bookType + '\'' +
                ", bookAbbreviate='" + bookAbbreviate + '\'' +
                ", classification='" + classification + '\'' +
                ", charCount='" + charCount + '\'' +
                ", abs='" + abs + '\'' +
                ", cover='" + cover + '\'' +
                ", bookcode='" + bookcode + '\'' +
                ", people='" + people + '\'' +
                ", organ='" + organ + '\'' +
                ", hot='" + hot + '\'' +
                ", keyword='" + keyword + '\'' +
                ", bookClass='" + bookClass + '\'' +
                ", hottype='" + hottype + '\'' +
                ", mainmsg='" + mainmsg + '\'' +
                ", epub='" + epub + '\'' +
                ", yearListSize=" + yearListSize +
                ", bookYearsList=" + bookYearsList +
                '}';
    }
}

