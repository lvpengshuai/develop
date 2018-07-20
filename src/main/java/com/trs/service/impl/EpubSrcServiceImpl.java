package com.trs.service.impl;

import com.trs.mapper.EpubSrcMapper;
import com.trs.model.EpubSrc;
import com.trs.service.EpubSrcService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by epro on 2017/8/24.
 */

@Service
public class EpubSrcServiceImpl implements EpubSrcService {

    @Resource
    private EpubSrcMapper epubSrcMapper;

    /**
     * 插入epubsrc
     * @param epubSrc
     * @return
     */
    public Map insertEpubSrc(EpubSrc epubSrc){
        Map addMap = new HashMap();
        // 添加收藏
        int addEpubSrc= epubSrcMapper.insertEpubSrc(epubSrc);
        if(addEpubSrc==1){
            //成功
            addMap.put("state", "0");
        }else {
            addMap.put("state", "1");
        }
        return addMap;
    }

    /**
     * 查找src
     * @return
     */
    public List<EpubSrc> selectByTitle(String bookCode, String title){
        List<EpubSrc> ad=epubSrcMapper.selectByTitle(bookCode,title);
        return ad;
    }

    /**
     * 删除src
     * @param bookCode
     * @return
     */
    public Map deleteSrc(String bookCode){
        Map dele = new HashMap();
        // 添加收藏
        int addEpubSrc= epubSrcMapper.deleteSrc(bookCode);
        if(addEpubSrc!=0){
            //成功
            dele.put("state", "成功");
        }else {
            dele.put("state", "失败");
        }
        return dele;
    }

}
