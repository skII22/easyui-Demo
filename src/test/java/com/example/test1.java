package com.example;

import com.example.Util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Filter;

@Slf4j
public class test1 {
    @Test
    public void test() throws ParseException {
        int a = 44594;
        Calendar calendar = new GregorianCalendar(1900,0,-1);
        int intDay = Integer.parseInt(String.valueOf(a));
        Date dd = DateUtils.addDays(calendar.getTime(),intDay);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US);
        String date =new SimpleDateFormat("yyyy-MM-dd").format(simpleDateFormat.parse(String.valueOf(dd)));


    }

    /**CTS时间
     * 时间格式转换
     * @throws ParseException
     */
    @Test
    public void t2() throws ParseException {
        String date = "Thu Aug 27 18:05:49 CST 2015";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date d = sdf.parse(date);
        String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);


        System.out.println(formatDate);
    }
    @Test
    public void list(){
        List l = new ArrayList();
        new ArrayList<>(l).ensureCapacity(100);
        l.add(2);
        l.add(3);
        l.add(1);
        l.add(1);

//        List list = l.subList(1, 2);
        System.out.println(l);
        Object o = new ArrayList<>(l).clone();
        System.out.println(o.toString());

        String [] str = {"a","b","c"};
        Object[] array = Arrays.stream(str).toArray();



    }
}
