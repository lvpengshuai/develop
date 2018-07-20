package com.trs.service;

import com.trs.core.util.Status;
import com.trs.model.PeriodicalAuthor;
import com.trs.model.Periodical;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by 李春雨 on 2017/3/3.
 */
public interface PeriodicalService {
    /**
     * 查询所有期刊资源并分页展示
     * @return
     */
    public Map findAll(Map<String, Object> map);

    /**
     * 查询所有数据总数用于分页
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
     * @param ids
     */
    public Status deleteDatasById(List<Integer> ids) throws Exception;

    /**
     * 通过id查询作者表中的数据
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
     * 发布与撤销发布
     * @param map
     * @return
     */
    public Status change(Map map);

    /**
     * 导入期刊
     * @param file
     * @param realPath
     * @return
     */
    public Status importFile(MultipartFile file, String realPath) throws Exception;

    /**
     * 编辑期刊
     * @param map
     * @return
     */
    public Map updatePeriodical(Map map);

    /**
     * 查询所有数据
     * @return
     */
    public List<Periodical> selectData();

    /**
     * 文件重命名
     * @param file
     * @param name
     */
    public void rename(File file, String name);
}
