package com.trs.mapper;

import com.trs.model.Standard;

import java.util.List;
import java.util.Map;

/**
 * Created by 李春雨 on 2017/3/9.
 */
public interface StandardMapper {

    /**
     * 查询所有标准资源并分页展示
     * @return
     */
    public List<Standard> findAll(Map<String, Object> map);

    /**
     * 查询数据总数用于分页
     * @return
     */
    public int total(Map<String, Object> map);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    public Standard findDatasById(int id);

    /**
     * 通过id删除资源
     * @param id
     */
    public void deleteDatasById(int id);

    /**
     * 发布资源
     * @param standard
     */
    public void change(Standard standard);

    /**
     * 导入期刊
     * @param standard
     * @return
     */
    public boolean add(Standard standard);

    /**
     * 修改标准
     * @param standard
     */
    public void updateStandard(Standard standard);

    /**
     * 查询所有信息
     * @return
     */
    public List<Standard> find();

    /**
     * 查询替代标准
     * @param replace
     * @return
     */
    public List<Standard> findDataByReplace(String replace);

    /**
     * 通过编号去重
     * @param bianhao
     * @return
     */
    public List<Standard> findStandarByBianHao(String bianhao);
}
