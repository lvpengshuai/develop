package com.trs.model;

public class KnowledgeReviewWithBLOBs extends KnowledgeReview {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_review.text
     *
     * @mbggenerated
     */
    private String text;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_review.lcoation
     *
     * @mbggenerated
     */
    private String lcoation;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_review.text
     *
     * @return the value of knowledge_review.text
     *
     * @mbggenerated
     */
    public String getText() {
        return text;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_review.text
     *
     * @param text the value for knowledge_review.text
     *
     * @mbggenerated
     */
    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_review.lcoation
     *
     * @return the value of knowledge_review.lcoation
     *
     * @mbggenerated
     */
    public String getLcoation() {
        return lcoation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_review.lcoation
     *
     * @param lcoation the value for knowledge_review.lcoation
     *
     * @mbggenerated
     */
    public void setLcoation(String lcoation) {
        this.lcoation = lcoation == null ? null : lcoation.trim();
    }
}