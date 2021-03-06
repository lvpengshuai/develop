package com.trs.model;

public class Config {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config.value
     *
     * @mbggenerated
     */
    private String value;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config.name
     *
     * @return the value of config.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config.name
     *
     * @param name the value for config.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config.remark
     *
     * @return the value of config.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config.remark
     *
     * @param remark the value for config.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config.value
     *
     * @return the value of config.value
     *
     * @mbggenerated
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config.value
     *
     * @param value the value for config.value
     *
     * @mbggenerated
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
}