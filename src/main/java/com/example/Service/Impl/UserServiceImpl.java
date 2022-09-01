package com.example.Service.Impl;

import com.example.Entity.User;
import com.example.Mapping.UserMapper;
import com.example.Service.UserService;
import com.example.common.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.*;

import static sun.util.calendar.CalendarUtils.isGregorianLeapYear;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    UserService userService;
    @Override
    public List<User> selectAll() {
        List<User> all = (List<User>) userMapper.findAll();
        return all;
    }

    @Override
    public Integer selectByidCard(String idcard) {
        List list = userMapper.selectbyIdCard(idcard);
        int size = list.size();
        return size;
    }

    @Override
    public List<User> selectById(Integer id) {
        Optional<User> byId = userMapper.findById(id);
        List list = Collections.singletonList(byId);
        return list;
    }



    @Override
    public User updateUser(@RequestParam Integer id, @RequestParam String name, @RequestParam String sex, @RequestParam String pin_name, @RequestParam String id_type, @RequestParam String id_card, @RequestParam Date birthday, @RequestParam String phone, @RequestParam String email) {

        Optional<User> users = userMapper.findById(id);
        User user = users.get();
        user.setName(name);
            user.setSex(sex);
            user.setPin_name(pin_name);

            user.setId_type(id_type);
            user.setId_card(id_card);

            user.setBirthday(birthday);


            user.setPhone(phone);


            user.setEmail(email);


        return userMapper.save(user);
    }



    @Override
    public Boolean insertUser(String name, String sex, String pin_name, String id_type, String id_card, Date birthday, String phone, String email) {
        User user = new User();
        if ("null".equals(name)||name.length()>5){
            throw new CustomException("姓名填写错误，请重新填写！");
        }

//        if ("男"==(sex)){
//        } else if ("女"==(sex)) {
//        }else {
//            throw new CustomException("证件类型选择错误，请重新选择！");
//        }

        if ("null".equals(pin_name)||pin_name.length()>20){
            throw new CustomException("姓名全拼填写错误，请重新填写！");
        }

//        if ("士官证"!=id_type||"身份证"!=id_type){
//
//            throw new CustomException("证件类型选择错误，请重新选择！");
//        }

//        if (id_card.length()!=15||id_card.length()!=18){
//            throw new CustomException("证件号码信息，请重新填写！");
//        }
        int year = birthday.getYear();
        int month = birthday.getMonth();
        int day = birthday.getDay();

        if (year <= 0) {
            throw new CustomException("日期有误");
        }
        if (month <= 0 || month > 12) {
            throw new CustomException("日期有误");
        }
        if (day <= 0 ) {
            throw new CustomException("日期有误");
        }

        if (month == 2 && day == 29 && !isGregorianLeapYear(year)) {
            throw new CustomException("日期有误");
        }

        if (phone.length()!=11){
            throw new CustomException("手机号码有误");
        }

            user.setName(name);
            user.setSex(sex);
            user.setPin_name(pin_name);
            user.setId_type(id_type);
            user.setId_card(id_card);
            user.setBirthday(birthday);
            user.setPhone(phone);
            user.setEmail(email);
        userMapper.save(user);
        return null;
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void insertUser(User user) {
//        userMapper.insertUser(user);
        userMapper.save(user);
    }


    @Override
    public com.example.common.Page selectpage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> pages = userMapper.findAll(pageable);
        List<User> list = pages.toList();
        long total = pages.getTotalElements();
        return com.example.common.Page.date(total,list);
    }


    @Override
    public Long findId(String name, String sex, String pin_name, String id_type, String id_card, Date birthday, String phone, String email) {
        Long id = userMapper.findId(name, sex,  pin_name,id_type,  id_card,  birthday, phone,  email);
        return id;
    }


}
