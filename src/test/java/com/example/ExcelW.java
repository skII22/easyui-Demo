package com.example;


import com.alibaba.excel.EasyExcel;
import com.example.Entity.User;
import com.example.Mapping.UserMapper;
import com.example.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Slf4j
public class ExcelW {

    @Autowired
    UserService userService;
    UserMapper userMapper;
    @Test
    public void write(){
        String filename = "D:\\IDM\\a.xlsx";
        Iterable<User> all = userMapper.findAll();
        log.info(String.valueOf(all));
        // 2 调用easyexcel里面的方法实现写操作
        // write方法两个参数：第一个参数文件路径名称，第二个参数实体类class
//        EasyExcel.write(filename,User.class).sheet().doWrite(list);
    }

}
