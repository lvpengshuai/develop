package com.trs.mapper;

import com.trs.model.ThesaurusCoreWord;

import java.util.List;
import java.util.Map;

public interface ThesaurusCoreWordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thesaurus_core_word
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thesaurus_core_word
     *
     * @mbggenerated
     */
    int insert(ThesaurusCoreWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thesaurus_core_word
     *
     * @mbggenerated
     */
    int insertSelective(ThesaurusCoreWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thesaurus_core_word
     *
     * @mbggenerated
     */
    ThesaurusCoreWord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thesaurus_core_word
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ThesaurusCoreWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thesaurus_core_word
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ThesaurusCoreWord record);

    List<ThesaurusCoreWord> selectByWord(Map map);
}