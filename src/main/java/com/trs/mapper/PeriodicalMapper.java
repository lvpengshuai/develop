package com.trs.mapper;

import com.trs.model.Periodical;
import com.trs.model.PeriodicalAuthor;

import java.util.List;
import java.util.Map;

/**
 * Created by 李春雨 on 2017/3/3.
 */
public interface PeriodicalMapper {
    /**
     * 查询所有期刊资源并分页展示
     * @return
     */
    public List<Periodical> findAll(Map<String, Object> map);

    /**
     * 返回所有数据总数用于分页
     * @return
     */
    public int total(Map map);

    /**
     * 通过id查询数据
     * @param id
     * @return
     */
    public Periodical findDatasById(int id);

    /**
     * 通过id删除数据
     * @param id
     */
    public void deleteDatasById(int id);

    /**
     * 通index_id查询数据
     * @param id
     * @return
     */
    public List<PeriodicalAuthor> findById(int id);

    /**
     * 通过id删除作者信息
     * @param id
     */
    public void deleteAuthorById(int id);

    /**
     * 发布对应的资源
     * @param periodical
     */
    public void change(Periodical periodical);

    /**
     * 导入期刊
     * @param periodical
     * @return
     */
    public boolean add(Periodical periodical);

    /**
     * 修改期刊
     * @param periodical
     */
    public void updatePeriodical(Periodical periodical);

    /**
     * 查询全部数据
     * @return
     */
    public List<Periodical> selectData();

    /**
     * 查询临时表数据
     * @return
     */
    public List<Periodical> select(Periodical periodical);

    /**
     * 通过杂志id和文章编号去重复
     * @param map
     * @return
     */
    public List<Periodical> selectDataByZaZhiId(Map map);


}
