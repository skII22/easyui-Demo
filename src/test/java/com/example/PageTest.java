package com.example;

import com.example.Entity.User;
import com.example.Mapping.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
class PageTest {

    @Test
    void contextLoads() {
    }
@Autowired
    UserMapper userMapper;
    @Test
    public void test1(){
        int page = 0; //page:当前页的索引。注意索引都是从 0 开始的。
        int size = 2;// size:每页显示 3 条数据
        PageRequest pageable= PageRequest.of(page, size);
        Page<User> p = this.userMapper.findAll(pageable);
        System.out.println("数据的总条数："+p.getTotalElements());
        System.out.println("总页数："+p.getTotalPages());
        List<User> list = p.getContent();
        for (User users : list) {
            System.out.println(users);
        }
    }

}
