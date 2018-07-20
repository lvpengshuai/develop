package com.trs.model;

public class BookYears {
    private Integer id;
    private String bookyear;
    private String bookcode;

    public String getBookcode() {
        return bookcode;
    }

    public void setBookcode(String bookcode) {
        this.bookcode = bookcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookyear() {
        return bookyear;
    }

    public void setBookyear(String bookyear) {
        this.bookyear = bookyear;
    }
}
