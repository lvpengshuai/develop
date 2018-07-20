package com.trs.core.breakxml;

public enum YearBook {
    世界经济年鉴("sjjjnj", "学科类年鉴"),
    中国文学年鉴("zgwxnj", "学科类年鉴"),
    中国哲学年鉴("zgzxnj", "学科类年鉴");


    private YearBook(String abbreviate, String classification) {
        this.abbreviate = abbreviate;
        this.classification = classification;
    }

    public static YearBook getValue(String info){
        if(info.equals("世界经济年鉴")){
            return 世界经济年鉴;
        }else if(info.equals("中国文学年鉴")){
            return 中国文学年鉴;
        }else if(info.equals("中国哲学年鉴")){
            return 中国哲学年鉴;
        }else{
            return null;
        }
    }

    private String abbreviate;
    private String classification;

    public String getAbbreviate() {
        return abbreviate;
    }

    public void setAbbreviate(String abbreviate) {
        this.abbreviate = abbreviate;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }



}
