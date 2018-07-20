package com.trs.service.impl;

import com.trs.mapper.CategoryBookMapper;
import com.trs.model.CategoryBook;
import com.trs.service.CategoryBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xu.bo on 2017/4/20.
 */
@Service
public class CategoryBookServiceImpl implements CategoryBookService {

    @Resource
    private CategoryBookMapper categoryBookMapper;

    @Override
    public CategoryBook getCategoryBookByNum(String num) {
        CategoryBook parentCategory = categoryBookMapper.findParentCategory(num);

        return parentCategory;
    }
}
