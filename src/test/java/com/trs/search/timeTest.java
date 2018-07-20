package com.trs.search;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/14.
 */
public class timeTest {
    @Test
    public void getThree(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        for(int i=0;i<4;i++){
            Date d = new Date();
            String dateNowStr = format.format(d);
            int n = Integer.parseInt(dateNowStr);
            System.out.println(n-i);
        }
    }
}
