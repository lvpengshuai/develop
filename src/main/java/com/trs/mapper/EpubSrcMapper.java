package com.trs.mapper;

import com.trs.model.EpubSrc;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
public interface EpubSrcMapper {

    /**
     * 插入到epubsrc表
     * @param epubSrc
     * @return
     */
    int  insertEpubSrc(EpubSrc epubSrc);

    /**
     * 查找src
     * @return
     */
    public List<EpubSrc> selectByTitle(@Param("bookCode") String bookCode, @Param("title") String title );


    /**
     * 删除src 根据bookcode
     * @param bookCode
     * @return
     */
    int deleteSrc(String bookCode);
}
