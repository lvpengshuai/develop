package com.trs.core.util;

import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;


/**
 * Created by lcy on 2017/4/13.
 */
public class AnsjUtil {

    public static void write(String keyWord) throws Exception {
        String key = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("%20", " ")
                + "ambiguity.txt";
        File file = new File(key);

        OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
        oStreamWriter.append(keyWord);
        oStreamWriter.close();
    }


    public static Map<String, List<String>> getValue(String keyWord) throws Exception {
        Map result = new HashMap();
        String name = null;
        String id = null;
        String type = null;
        List tmp = new ArrayList();
        String dic = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("%20", " ")
                + "ambiguity.txt";
        Forest forest = Library.makeForest(dic);

        String content = keyWord;
        GetWord udg = forest.getWord(content);

        String temp = null;
        while ((temp = udg.getFrontWords()) != null) {
            name = temp;
            id = udg.getParam(1);
            type = udg.getParam(0);
            tmp.add(name + ";" + id + ";" + type);
        }
        Set l = new TreeSet();
        Set<String> set = new TreeSet();
        List<String> tempList = new ArrayList<>();
        set.addAll(tmp);
        for (String s : set) {
            l.add(new StrLenComparator(s));
        }
        Iterator it = l.iterator();
        while (it.hasNext()) {
            StrLenComparator next = (StrLenComparator) it.next();
            tempList.add(next.getName());
        }
        result.put("name", tempList);
        return result;

    }

    public static void main(String[] args) throws Exception {
        getValue("硝酸钠物探工程是我们的火星探测计划大发大沙发大发断层要素");
    }
}
