package com.trs.service.impl;

import com.trs.core.util.Util;
import com.trs.mapper.BookDetailsMapper;
import com.trs.model.BookDetails;
import com.trs.service.BookDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by xuxuecheng on 2017/08/24.
 */
@Service
public class BookDetailsServiceImpl implements BookDetailsService {
    @Resource
    private BookDetailsMapper bookDetailsMapper;


    @Override
    public List<BookDetails> getEntrys(String bookcode, String entry, Integer pageIndex, Integer pageSize) {
        return bookDetailsMapper.getEntrys(bookcode, entry, pageIndex, pageSize);
    }

    @Override
    public Integer getEntrysCount(String bookcode, String entry) {
        return bookDetailsMapper.getEntrysCount(bookcode, entry);
    }

    @Override
    public List<BookDetails> getBookDetailsList(BookDetails bookDetails) {
        return bookDetailsMapper.getBookDetailsList(bookDetails);
    }

    @Override
    public BookDetails getBookDetailsByZid(String zid,String bookCode) {
        return bookDetailsMapper.getBookDetailsByZid(zid,bookCode);
    }

    @Override
    public int getBookListByZid(String zid,String bookcode) {
        int result = 0;
        BookDetails bookDetails = bookDetailsMapper.getBookListByZid(zid,bookcode);
        if (bookDetails != null) {
            int readCount = Util.toInt(bookDetails.getReadCount(), 0);
            bookDetails.setReadCount(++readCount);
            String upValue="readCount="+readCount;
            String upWhere="zid='"+zid+"' and "+"bookcode='"+bookcode+"'";
            //System.out.println("修改readCount的sql=="+upWhere);
            int rs= Util.updateReadCount("cssp_bookdetail",upValue,upWhere);
            setReadCount(bookDetails);
        }

        return result;
    }

    @Override
    public List<BookDetails> getHotArticles(String bookCode) {
        return bookDetailsMapper.getHotArticles(bookCode);
    }

    @Override
    public BookDetails getFirstCatalogByZid(String zid, String bookcode) {
        return bookDetailsMapper.getFirstCatalogByZid(zid, bookcode);
    }

    @Override
    public List<BookDetails> getHtmlContent(String bookcode, String date) {
//        //查询全部的大事记
//        List<BookDetails> htmlContent = bookDetailsMapper.getHtmlContent(bookcode);
//        //为了防止date传过来报空指针异常
//        Optional<String> optional = Optional.ofNullable(date);
//        //创建返回collector
//        List<BookDetails> bookDetailsList = null;
//        //封装数据
//        if (!optional.isPresent()) {
//            Optional<Date> first = htmlContent.stream().map(BookDetails::getExdate).sorted().findFirst();
//            Date date1 = first.orElse(null);
//            bookDetailsList = htmlContent.stream().filter(x -> sameDate(date1,x.getExdate())).collect(Collectors.toList());
//        } else {
//            //得到Date
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                Date date2 = sdf.parse(optional.orElse(null));
//                bookDetailsList = htmlContent.stream().filter(x -> sameDate(date2,x.getExdate())).collect(Collectors.toList());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
        return bookDetailsMapper.getHtmlContent(bookcode);
    }

    @Override
    public List<Map<String, Object>> getDsjCitiao(List<BookDetails> list) {
        List<Map<String, Object>> listRtn = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            // 遍历12个月份分别取出当月大事记
            Map<String, Object> map = new HashMap<>();
            List<BookDetails> listDsj = new ArrayList<>();
            for (BookDetails bd : list) {
                // 按照月份分组大数据记事
                if (i == bd.getExdate().getMonth() + 1) {
                    listDsj.add(bd);
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
                bd.setExdata(formatter.format(bd.getExdate()));
                // 修改大事记中<p>标签标记的大事记
                bd.setHtmlContent(bd.getHtmlContent().replace("<p>","").replace("</p>", "\n"));
            }
            // 按照月份和当月大事记列表做Map封装
            //if(listDsj != null && listDsj.size() != 0) {
                map.put("month", String.valueOf(i));
                map.put("event", listDsj);
                listRtn.add(map);
            //}
        }
        return listRtn;
    }

    public static boolean sameDate(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(d1).equals(fmt.format(d2));
    }

    @Override
    public int setReadCount(BookDetails bookDetails) {
        return bookDetailsMapper.setReadCount(bookDetails);
    }

    @Override
    public Integer getHtmlContentNum(String bookcode) {
        return bookDetailsMapper.getHtmlContentNum(bookcode);
    }

    @Override
    public List<BookDetails> getBookDetailsAuthor(String bookcode, Integer pageIndex) {
        return bookDetailsMapper.getBookDetailsAuthor(bookcode, pageIndex);
    }

    @Override
    public Integer getBookDetailsAuthorNum(String bookcode) {
        return bookDetailsMapper.getBookDetailsAuthorNum(bookcode);
    }

    @Override
    public Map getBookDetailMap(String startTime, String endTime, String type, String ads, int pageSize, int pageIndex, String bookcode) {
        HashMap valMap = new HashMap();
        bookcode = bookcode + "[0-9]";
        List<BookDetails> bookDetailList = bookDetailsMapper.getHotBookDetailMap(startTime, endTime, type, ads, bookcode);
        int count = bookDetailList.size();
        int pageCount = (count % pageSize) == 0 ? count / pageSize : count / pageSize + 1;
        valMap.put("pageCount", pageCount);
        int start = pageIndex - 1;
        ArrayList resultList = new ArrayList();
        for (int i = start * pageSize; i < start * pageSize + pageSize && i < count; i++) {
            resultList.add(bookDetailList.get(i));
        }
        valMap.put("resultList", resultList);
        return valMap;
    }

    @Override
    public Map getBookDetailMap(String startTime, String endTime, String type, String ads, String bookcode) {
        HashMap valMap = new HashMap();
        List<BookDetails> bookDetailList = bookDetailsMapper.getBookDetailMap(startTime, endTime, type, ads, bookcode);
        valMap.put("resultList", bookDetailList);
        return valMap;
    }

    @Override
    public Integer getBookDetailMapNum(String bookcode) {
        return bookDetailsMapper.getBookDetailMapNum(bookcode, "会议词条");
    }

    @Override
    public List<BookDetails> getBookDetailPaper(String bookcode, String pageIndex) {
        List<BookDetails> bookDetails = bookDetailsMapper.getEntrys(bookcode, "论文词条", (Integer.parseInt(pageIndex) - 1) * 9, 9);
        return bookDetails;
    }

    @Override
    public Integer getBookDetailPaperCount(String bookcode) {
        Integer entrysCount = bookDetailsMapper.getEntrysCount(bookcode, "论文词条");
        return entrysCount;
    }

    @Override
    public List<BookDetails> getBookDetailTopic(String bookcode, String source, String pageIndex, String currPage) {
        List<BookDetails> bookDetailTopic = bookDetailsMapper.getBookDetailTopic(bookcode, source, (Integer.parseInt(pageIndex) - 1) * 4, Integer.parseInt(currPage));
        return bookDetailTopic;
    }

    @Override
    public Integer getBookDetailTopicNum(String bookcode, String source) {
        Integer bookDetailTopicNum = bookDetailsMapper.getBookDetailTopicNum(bookcode, source);
        return bookDetailTopicNum;
    }

    @Override
    public List getBookDetailsMechanism(String bookcode, String title,String sourceType,String titleType, String PageIndex, String currPage) {
        //把当前页数和一页个数转换成Integer
        Integer pageIndexNum = Integer.parseInt(PageIndex);
        Integer currPageNum = Integer.parseInt(currPage);
        //用新list去集合前台的数据
        List list = new ArrayList<>();
        if(sourceType.equals("3")){
            //获取机构词条数据
            String bookDetailsMechanism = bookDetailsMapper.getBookDetailsMechanism(bookcode, title,sourceType);
            //满足条件执行
            if (bookDetailsMechanism != null && "" != bookDetailsMechanism) {
                //用</p><p>去切割字符串得到数组
                String[] split = bookDetailsMechanism.split("</p><p>");
                //用切割后的数组得到新的stream流
                Stream<String> stream = Arrays.stream(split);
                //使用stream流去操作数据
                List<String> collect = stream.skip((pageIndexNum - 1) * currPageNum).limit(currPageNum).collect(Collectors.toList());
                //使用lambda去实现计算页数
                Integer integer = streamUtilPage(split, currPageNum, (x, y) -> y > 0 ? x + 1 : x);
                list.add(collect);
                list.add(integer);
                list.add(PageIndex);
                list.add("3");
                return list;
            } else {
                list.add(new ArrayList<>());
                return list;
            }
        }else {
            int pageBegin = (Integer.parseInt(PageIndex) - 1) * Integer.parseInt(currPage);
            List<BookDetails> bookDetailsMechanism = bookDetailsMapper.getBookDetailsMechanismType(bookcode,titleType,sourceType,pageBegin,Integer.parseInt(currPage));
            Integer count=bookDetailsMapper.getBookDetailsMechanismTypeCount(bookcode,titleType,sourceType);
            int page1 = count / currPageNum;
            int page2 = count % currPageNum;
            if(page2>0){
                page1+=1;
            }
            if(bookDetailsMechanism.size()!=0){
                list.add(bookDetailsMechanism);
                list.add(page1);
                list.add(PageIndex);
                list.add(bookDetailsMechanism.get(0).getSource());
            } else {
                list.add(new ArrayList<>());
            }
            return list;
        }




    }

    private Integer streamUtilPage(String[] split, Integer currPageNum, BinaryOperator<Integer> binaryOperator) {
        int length = split.length;
        int page1 = length / currPageNum;
        int page2 = length % currPageNum;
        return binaryOperator.apply(page1, page2);
    }

    @Override
    public Integer getBookDetailsMechanismNum(String bookcode) {
        return bookDetailsMapper.getBookDetailsMechanismNum(bookcode);
    }

    @Override
    public List<BookDetails> getBookDetailsMechanismTitle(String bookcode) {
        List<BookDetails> list = bookDetailsMapper.getBookDetailsMechanismTitle(bookcode);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTitle(Util.rmNumIndex(list.get(i).getTitle()));
        }
        return list;
    }

    @Override
    public List<BookDetails> getDirectoryByFid(String fid, String bookcode) {
        List<BookDetails> directoryByZid = bookDetailsMapper.getDirectoryByZid(fid, bookcode);
        Stream<BookDetails> stream = directoryByZid.stream();
        List<BookDetails> collect = stream.filter(x -> bookcode.equals(x.getBookcode()) && fid.equals(x.getFid())).collect(Collectors.toList());
        return collect;
    }

    @Override
    public Map<String, Integer> getCountByEntry() {
        return bookDetailsMapper.getCountByEntry();
    }

}
