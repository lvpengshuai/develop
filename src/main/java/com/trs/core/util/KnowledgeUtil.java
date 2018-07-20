package com.trs.core.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by panyan on 2017/3/13.
 */
public class KnowledgeUtil {
    public static String replaceHtmlTag(String str) {
        if (str.contains("[") && str.contains("]")) {
            str = str.replace("[", "<");
            str = str.replace("]", ">");
            return str;
        }
        return str;
    }

    public static String toWord(String str) {
        Pattern p = Pattern.compile("\\[" + "(.*?)" + "\\]");
        Matcher m = p.matcher(str);
        ArrayList<String> strs = new ArrayList<String>();
        while (m.find()) {
            strs.add(m.group(1));
        }
        for (String s : strs) {
            str = str.replace(s, "");
        }
        if (str.contains("[") && str.contains("]")) {
        }
        str = str.replace("[", "").replace("]", "");
        return str;
    }

    //获取所有线程
    public static Thread[] findAllThreads() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;

    /* 遍历线程组树，获取根线程组 */
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
    /* 激活的线程数加倍 */
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];

    /* 获取根线程组的所有线程 */
        int actualSize = topGroup.enumerate(slackList);
    /* copy into a list that is the exact size */
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        return (list);
    }

    //spilt Set
    public static List<Set> spilt13Set(Set<String> set) {
        Set<String> set1 = new HashSet();
        Set<String> set2 = new HashSet();
        Set<String> set3 = new HashSet();
        Set<String> set4 = new HashSet();
        Set<String> set5 = new HashSet<>();
        Set<String> set6 = new HashSet<>();
        Set<String> set7 = new HashSet<>();
        Set<String> set8 = new HashSet<>();
        Set<String> set9 = new HashSet<>();
        Set<String> set10 = new HashSet<>();
        Set<String> set11 = new HashSet<>();
        Set<String> set12 = new HashSet<>();
        Set<String> set13 = new HashSet<>();
        Set<String> set14 = new HashSet<>();

        List list = new ArrayList();
        int splite = set.size() / 14;
        int jishu = 0;
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (jishu < splite) {
                set1.add(next);
            }
            if (jishu > splite && jishu < splite * 2) {
                set2.add(next);
            }
            if (jishu > splite * 2 && jishu < splite * 3) {
                set3.add(next);
            }
            if ((jishu > splite * 3 && jishu < splite * 4)) {
                set4.add(next);
            }
            if ((jishu > splite * 4 && jishu < splite * 5)) {
                set5.add(next);
            }
            if ((jishu > splite * 5 && jishu < splite * 6)) {
                set6.add(next);
            }
            if ((jishu > splite * 6 && jishu < splite * 7)) {
                set7.add(next);
            }
            if ((jishu > splite * 7 && jishu < splite * 8)) {
                set8.add(next);
            }
            if ((jishu > splite * 8 && jishu < splite * 9)) {
                set9.add(next);
            }
            if ((jishu > splite * 9 && jishu < splite * 10)) {
                set10.add(next);
            }
            if ((jishu > splite * 10 && jishu < splite * 11)) {
                set11.add(next);
            }
            if ((jishu > splite * 11 && jishu < splite * 12)) {
                set12.add(next);
            }
            if ((jishu > splite * 13 && jishu < splite * 14)) {
                set13.add(next);
            }
            jishu++;
        }
        list.add(set1);
        list.add(set2);
        list.add(set3);
        list.add(set4);
        list.add(set5);
        list.add(set7);
        list.add(set8);
        list.add(set9);
        list.add(set10);
        list.add(set11);
        list.add(set12);
        list.add(set13);


        return list;
    }

    //spilt 20 Set
    public static List<Set> spilt20Set(Set<String> set) {
        Set<String> set1 = new HashSet();
        Set<String> set2 = new HashSet();
        Set<String> set3 = new HashSet();
        Set<String> set4 = new HashSet();
        Set<String> set5 = new HashSet<>();
        Set<String> set6 = new HashSet<>();
        Set<String> set7 = new HashSet<>();
        Set<String> set8 = new HashSet<>();
        Set<String> set9 = new HashSet<>();
        Set<String> set10 = new HashSet<>();
        Set<String> set11 = new HashSet<>();
        Set<String> set12 = new HashSet<>();
        Set<String> set13 = new HashSet<>();
        Set<String> set14 = new HashSet<>();
        Set<String> set15 = new HashSet<>();
        Set<String> set16 = new HashSet<>();
        Set<String> set17 = new HashSet<>();
        Set<String> set18 = new HashSet<>();
        Set<String> set19 = new HashSet<>();
        Set<String> set20 = new HashSet<>();

        List list = new ArrayList();
        int splite = set.size() / 21;
        int jishu = 0;
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (jishu < splite) {
                set1.add(next);
            }
            if (jishu > splite && jishu < splite * 2) {
                set2.add(next);
            }
            if (jishu > splite * 2 && jishu < splite * 3) {
                set3.add(next);
            }
            if ((jishu > splite * 3 && jishu < splite * 4)) {
                set4.add(next);
            }
            if ((jishu > splite * 4 && jishu < splite * 5)) {
                set5.add(next);
            }
            if ((jishu > splite * 5 && jishu < splite * 6)) {
                set6.add(next);
            }
            if ((jishu > splite * 6 && jishu < splite * 7)) {
                set7.add(next);
            }
            if ((jishu > splite * 7 && jishu < splite * 8)) {
                set8.add(next);
            }
            if ((jishu > splite * 8 && jishu < splite * 9)) {
                set9.add(next);
            }
            if ((jishu > splite * 9 && jishu < splite * 10)) {
                set10.add(next);
            }
            if ((jishu > splite * 10 && jishu < splite * 11)) {
                set11.add(next);
            }
            if ((jishu > splite * 11 && jishu < splite * 12)) {
                set12.add(next);
            }
            if ((jishu > splite * 13 && jishu < splite * 14)) {
                set13.add(next);
            }
            if ((jishu > splite * 14 && jishu < splite * 15)) {
                set14.add(next);
            }
            if ((jishu > splite * 15 && jishu < splite * 16)) {
                set15.add(next);
            }
            if ((jishu > splite * 16 && jishu < splite * 17)) {
                set16.add(next);
            }
            if ((jishu > splite * 17 && jishu < splite * 18)) {
                set17.add(next);
            }
            if ((jishu > splite * 18 && jishu < splite * 19)) {
                set18.add(next);
            }
            if ((jishu > splite * 19 && jishu < splite * 20)) {
                set19.add(next);
            }
            if ((jishu > splite * 20 && jishu < splite * 21)) {
                set20.add(next);
            }
            jishu++;
        }
        list.add(set1);
        list.add(set2);
        list.add(set3);
        list.add(set4);
        list.add(set5);
        list.add(set7);
        list.add(set8);
        list.add(set9);
        list.add(set10);
        list.add(set11);
        list.add(set12);
        list.add(set13);
        list.add(set14);
        list.add(set15);
        list.add(set16);
        list.add(set17);
        list.add(set18);
        list.add(set19);
        list.add(set20);


        return list;
    }

    //过滤image标签
    public static String repliceImageByEl(String msg) {
        Pattern p = Pattern.compile("(?:<image[^>]*>)(.*?)(?:<\\/image[^>]*>)");//匹配<title>开头，</title>结尾的文档
        Matcher m = p.matcher(msg);//开始编译
        while (m.find()) {
            msg = msg.replace(m.group(1), "");//获取被匹配的部分
        }
        return msg;
    }
}