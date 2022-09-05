package com.example.Service;

import com.example.Entity.User;
import com.example.common.Page;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;
import java.text.ParseException;
import java.util.List;


    public interface UserService {
        /** 查询所有  */
        List<User> selectAll();

        Integer selectByidCard(String idcard);
        /**  通过id查询 */
        List<User> selectById(Integer id);
        /**更新用户信息*/
        User updateUser(@RequestParam Integer id, @RequestParam String name, @RequestParam String sex, @RequestParam String pin_name, @RequestParam String id_type, @RequestParam String id_card, @RequestParam Date birthday, @RequestParam String phone, @RequestParam String email);

        /** 增加 */
        Boolean insertUser(String name, String sex, String pin_name, String id_type, String id_card, Date birthday, String phone, String email) throws ParseException;

        /** 删除 */
        void deleteUser(Integer id);

        void insertUser(User user);

        /**
         * 分页查询
         */
        Page selectpage(int page, int pageSize);


        /**查找id*/
        Long findId(String name, String sex, String pin_name, String id_type, String id_card, Date birthday, String phone, String email);

    }




