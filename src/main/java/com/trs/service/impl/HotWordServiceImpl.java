package com.trs.service.impl;

import com.trs.core.util.JDBConnect;
import com.trs.mapper.HotWordMapper;
import com.trs.model.Book;
import com.trs.model.BookClassify;
import com.trs.model.HotWord;
import com.trs.service.HotWordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class HotWordServiceImpl implements HotWordService {

    @Resource
    private HotWordMapper hotWordMapper;

    @Override
    public void addHotWord(String hot) {
        HotWord hotWord = new HotWord();
        hotWord.setWord(hot);
        hotWord.setStatus("1");
        hotWord.setGmt_create(LocalDate.now().toString());
        hotWord.setGmt_modified(LocalDate.now().toString());
        hotWordMapper.addHotWord(hotWord);
    }

    @Override
    public List<HotWord> getHotWords() {
        return hotWordMapper.getHotWords();
    }

    @Override
    public List<HotWord> getIndexHotWords() {
        return hotWordMapper.getIndexHotWords();
    }
    @Override
    public void deleteHotWord(String id) {
        hotWordMapper.deleteHotWord(Integer.parseInt(id));
    }

    @Override
    public void updateHotWord(String id, String status) {
        if ("0".equals(status)) {
            hotWordMapper.updateHotWord(id, "1");
        } else if ("1".equals(status)) {
            hotWordMapper.updateHotWord(id, "0");
        }
    }

    @Override
    public void updateHotWordHot(String id, String hot) {
        hotWordMapper.updateHotWordHot(id, hot);
    }

    @Override
    public List<Book> getBookAll(String pageSize, String currPage, String search) {
        if (search!=null){
            search="%"+search+"%";
        }
        return hotWordMapper.getBookAll(Integer.parseInt(pageSize),Integer.parseInt(currPage),search);
    }

    @Override
    public Integer getBookAllNum() {
        return hotWordMapper.getBookAllNum();
    }


    @Override
    public void updateBookHotType(List<String> id, String state) {
        for (String s : id ) {
            hotWordMapper.updateBookHotType(s, state);
        }
    }

    @Override
    public void updateBookClassofyOrder(List<Map<String, Object>> list){
        JDBConnect db = new JDBConnect();
        String sql = "";
        for(Map<String, Object> map:list){
            sql += "update book_classify set sortkey='" + map.get("sortkey") + "' where bookabbreviate='" + map.get("bookabbreviate") + "' ;";
        }
        System.out.println(sql);
        db.insertTran(sql);
    }

    @Override
    public List<BookClassify> getBookClassofyOrder(){
        return hotWordMapper.getBookClassifyList();
    }
}
