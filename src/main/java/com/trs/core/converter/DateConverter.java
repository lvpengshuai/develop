package com.trs.core.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zly on 2017-5-11.
 */
public class DateConverter implements Converter<String,Date> {

    @Override
    public Date convert(String source) {
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
            date = sdf.parse(source);
        } catch (ParseException e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
            try {
                date = sdf.parse(source);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return date;
    }
}
