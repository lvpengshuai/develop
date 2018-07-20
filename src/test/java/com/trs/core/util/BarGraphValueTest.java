package com.trs.core.util;


import net.sf.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static sun.tools.jstat.Alignment.keySet;

/**
 * Created by Administrator on 2017/9/8.
 */
public class BarGraphValueTest {

    @Test
    public void mainTest(){
        /*--------------name--------------*/
        String[] names = new String[3];
        names[0]="name1";
        names[1]="name2";
        names[2]="name3";
        /*-----------year --> count--------------*/
        HashMap lastMap1 = new HashMap();
        lastMap1.put("1990",10);
        lastMap1.put("2000",20);
        lastMap1.put("2017",30);

        HashMap lastMap2 = new HashMap();
        lastMap2.put("1991",11);
        lastMap2.put("2001",21);
        lastMap2.put("2018",31);

        HashMap lastMap3 = new HashMap();
        lastMap3.put("1992",12);
        lastMap3.put("2003",22);
        lastMap3.put("2019",32);

        HashMap twoMap = new HashMap();

        twoMap.put(names[0],lastMap1);
        twoMap.put(names[1],lastMap2);
        twoMap.put(names[2],lastMap3);


       // System.out.println(JSONObject.fromObject(twoMap));
        HashMap hashMap = new HashMap();
        HashSet<Object> objectSet = new HashSet<>();

        for (Object key1 : twoMap.keySet()) {
            Map valMap = (Map) twoMap.get(key1);
            for (Object key2 : valMap.keySet()) {
                objectSet.add(key2);
            }
        }

        for (Object str : objectSet) {
            HashMap hashMap1 = new HashMap();
            HashMap hashMap2 = new HashMap();
            HashSet hashSet = new HashSet();
            for (Object key1 : twoMap.keySet()) {


                Map valMap = (Map) twoMap.get(key1);
                int num =0;
                for (Object key2 : valMap.keySet()) {
                    if(str.equals(key2)){
                        int o = (int) valMap.get(key2);
                        num=o;
                        break;
                    }

                }
                hashMap2.put(key1,num);
                hashSet.add(hashMap2);
            }
            hashMap1.put(str,hashMap2);
            hashMap.putAll(hashMap1);

        }
        System.out.println(JSONObject.fromObject(hashMap));

    }


}
