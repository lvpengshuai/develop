package com.trs.core.util;


/**
 * Created by lcy on 2017/6/14.
 */
public class StrLenComparator implements Comparable {
    private String name;

    public StrLenComparator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        System.out.println("hha ");
        if(!(o instanceof StrLenComparator))
            throw new RuntimeException("This is not a instance of Class \"StrLenComparator\" ");

        StrLenComparator o1 = (StrLenComparator)o;
        if(this.name.split(";")[0].length() > o1.name.split(";")[0].length()){
            return 1;
        }else if(this.name.split(";")[0].length() == o1.name.split(";")[0].length()){
            return 1;
        }

        return -1;
    }
}
