/*
package com.trs.web.author;

import com.trs.core.util.StringUtil;
import com.trs.mapper.*;
import com.trs.model.*;
import com.trs.service.BookService;
import com.trs.service.PeriodicalService;
//import com.trs.service.StandardService;
import com.trs.service.impl.SpringServiceTestBase;
import org.apache.http.auth.AUTH;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

*/
/**
 * Created by lcy on 2017/6/23.
 *//*

public class AuthorTest extends SpringServiceTestBase {

    @Resource
    private StandardMapper standardMapper;

    @Resource
    private PeriodicalMapper periodicalMapper;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private AuthorMapper authorMapper;

    @Resource
    private AuthorResourceMapper authorResourceMapper;


    @Test
    public void test() throws Exception {

        // 读取电子书作者
       */
/* List<Book> books = bookMapper.selectData();
        for (Book book : books) {
            String author = book.getBookAuthor();
            String[] split = author.split(";");
            for (int i = 0; i < split.length; i++) {
                System.out.println("电子书作者：" + split[i]);

                // 检测作者库是否存在该作者，如果存在则跳过不做处理
                List<Author> dataByAuthorName = authorMapper.findDataByAuthorName(split[i].replaceAll(";", ""));
                if(dataByAuthorName.size() > 0){
                 for(Author authorResource1 :dataByAuthorName){
                        // 插入作者资源对应关系
                        AuthorResource authorResource = new AuthorResource();
                        authorResource.setType("1");
                        authorResource.setAuthorId(authorResource1.getId()+"");
                        authorResource.setResourceId(book.getId()+"");
                        authorResourceMapper.insert(authorResource);
                    }
                    continue;
                }
                Author author1 = new Author();
                author1.setOrganization("");
                author1.setName(split[i].replaceAll(";", ""));
                authorMapper.add(author1);

                // 插入作者资源对应关系
                AuthorResource authorResource = new AuthorResource();
                authorResource.setType("1");
                authorResource.setAuthorId(author1.getId()+"");
                authorResource.setResourceId(book.getId()+"");
                authorResourceMapper.insert(authorResource);
            }
        }*//*


        // 读取期刊作者
        List<Periodical> periodicals = periodicalMapper.selectData();
        for (Periodical periodical : periodicals) {
            String lianXiZuoZhe = periodical.getLianXiZuoZhe();
            if (StringUtil.isEmpty(lianXiZuoZhe)) {
                continue;
            }
            String[] split = lianXiZuoZhe.split(";");
            for (int i = 0; i < split.length; i++) {
                System.out.println("期刊作者：" + split[i]);
                List<Author> dataByAuthorName = authorMapper.findDataByAuthorName(split[i]);
                if (dataByAuthorName.size() > 0) {
                    for (Author authorResource1 : dataByAuthorName) {
                        // 插入作者资源对应关系
                        AuthorResource authorResource = new AuthorResource();
                        authorResource.setType("2");
                        authorResource.setAuthorId(authorResource1.getId() + "");
                        authorResource.setResourceId(periodical.getId() + "");
                        authorResourceMapper.insert(authorResource);
                    }
                    continue;
                }
                Author author1 = new Author();
                author1.setOrganization("");
                author1.setName(split[i]);
                authorMapper.add(author1);

                // 插入作者资源对应关系
                AuthorResource authorResource = new AuthorResource();
                authorResource.setType("2");
                authorResource.setAuthorId(author1.getId() + "");
                authorResource.setResourceId(periodical.getId() + "");
                authorResourceMapper.insert(authorResource);
            }
        }

        // 读取标准作者
       */
/* List<Standard> standards = standardMapper.find();
        for (Standard standard : standards) {
            String drafter = standard.getDrafter();
            if (StringUtil.isEmpty(drafter)) {
                continue;
            }
            String[] split = drafter.split("、");
            for (int i = 0; i < split.length; i++) {
                System.out.println("标准作者：" + split[i]);
                List<Author> dataByAuthorName = authorMapper.findDataByAuthorName(split[i]);
                if (dataByAuthorName.size() > 0) {
                    for (Author authorResource1 : dataByAuthorName) {
                        // 插入作者资源对应关系
                        AuthorResource authorResource = new AuthorResource();
                        authorResource.setType("3");
                        authorResource.setAuthorId(authorResource1.getId() + "");
                        authorResource.setResourceId(standard.getId() + "");
                        authorResourceMapper.insert(authorResource);
                    }
                    continue;
                }
                Author author1 = new Author();
                author1.setOrganization("");
                author1.setName(split[i]);
                authorMapper.add(author1);

                // 插入作者资源对应关系
                AuthorResource authorResource = new AuthorResource();
                authorResource.setType("3");
                authorResource.setAuthorId(author1.getId() + "");
                authorResource.setResourceId(standard.getId() + "");
                authorResourceMapper.insert(authorResource);
            }
            }*//*

            System.out.println("处理完成，请测试");
        }
    }
*/
