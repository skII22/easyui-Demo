package com.example;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.example.Entity.User;
import com.example.Util.UserDataListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExcelR {
    @Test
    public void test4(){
        String filename = "D:\\IDM\\b.xls";
        EasyExcel.read(filename, User.class,new UserDataListener()).sheet().doRead();
    }

    @Test
    public void testa(){
        List list = new ArrayList<>();
        EasyExcel.read("D:\\IDM\\b.xls", User.class, new UserDataListener() {

            // 每读取一行就调用该方法
            @Override
            public void invoke(User user, AnalysisContext analysisContext) {
                list.add(user);
            }

            // 全部读取完成就调用该方法
        }).sheet().doRead();

        list.forEach(System.out::println);

    }
}
