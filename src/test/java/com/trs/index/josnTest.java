package com.trs.index;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xubo on 2017/4/13.
 */
public class josnTest {

    @Test
    public void jsontest(){
        /*ArrayList arrayList1 = new ArrayList();
        HashMap hashMap = new HashMap();
        for(int i =0;i<5;i++){
            ArrayList arrayList = new ArrayList();
            HashMap map = new HashMap();
            map.put("sss","");


            hashMap.put("sub",arrayList);
        }
        arrayList1.add(hashMap);*/
    }

    @Test
    public void spilt(){
        String str = "TQ414-62";
        String stra = str.split("-")[0];
        System.out.println(stra);
    }

}
