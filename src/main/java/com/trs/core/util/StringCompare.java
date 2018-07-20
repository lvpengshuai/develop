package com.trs.core.util;

import java.util.Comparator;

/**
 * Created by pz on 2017/4/26.
 */
public class StringCompare implements Comparator {
    public int compare(Object o1, Object o2) {
        String time1 = o1.toString();
        String time2 = o2.toString();
        if ((StringUtil.stringToAscii(time1)) > (StringUtil.stringToAscii(time2))) {
            return 1;
        } else if ((StringUtil.stringToAscii(time1)) == (StringUtil.stringToAscii(time2))) {
            return 0;
        } else {
            return -1;
        }
    }


}
