package com.trs.core.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pz on 2017/3/27.
 */
public class KnowledgeUtilTest {
    public static void main(String[] args) {

            String str = "[image]化学基础（第四版）/Image/14499_186_01.jpg[/image]";
            Pattern p = Pattern.compile("\\["+"(.*?)"+"\\]");
            Matcher m = p.matcher(str);
            ArrayList<String> strs = new ArrayList<String>();
            while (m.find()) {
                strs.add(m.group(1));
            }
            for (String s : strs){
                System.out.println(s);
            }

    }
    @Test
    public void RegTest(){
        String result = KnowledgeUtil.repliceImageByEl("乙酰氯是最重要的酰卤。在工业上它可用乙酸与三氯化磷、五氯化磷或亚硫酰氯作用来制取。<image>化学基础（第二版）/images/02920-27w6-01.jpg</image><title>17.2.3.1　乙酰氯</title>　　　　　　　　　　　　　亚硫酰氯注意，用亚硫酰氯制备乙酰氯的方法所得产品较纯，因为副产物均为气体，更易于分离提纯。乙酰<image>化学基础（第二版）/images/02920-276-01.jpg</image>氯为无色有刺激性气味的液体，沸点52℃。在空气中因被水解而冒白烟(HCl)，所以要密封保存。它的主要用途是作乙酰化剂。<image>化学基础（第二版）/images/02920-276-d01.jpg</image>");
        System.out.println(result);
    }


}
