package com.example.Controller;

import com.example.Entity.User;
import com.example.Service.UserService;
import com.example.Util.IDCardUtil;
import com.example.common.CustomException;
import com.example.common.Page;
import com.example.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;
import java.text.ParseException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     *
     * 添加用户信息
     * @param name
     * @param sex
     * @param pin_name
     * @param id_type
     * @param id_card
     * @param birthday
     * @param phone
     * @param email
     */
    @PostMapping("/save")
    public Result save(@RequestParam String name,  @RequestParam String sex, @RequestParam String pin_name, @RequestParam String id_type, @RequestParam String id_card, @RequestParam Date birthday, @RequestParam String phone ,@RequestParam String email){
        if (!name.matches("^([\\u4e00-\\u9fa5]{2,20}|[a-zA-Z.\\s]{2,20})$")){
            throw new CustomException("名字格式不正确，请重新填写！");
        }
        if (!pin_name.matches("^([\\u4e00-\\u9fa5]{2,20}|[a-zA-Z.\\s]{2,20})$")){
            throw new CustomException("名字拼写格式不正确，请重新填写！");
        }
        if (IDCardUtil.idCardValidate(id_card)==false){
            throw new CustomException("身份证号码有误，请重新输入！");
        }
        if (userService.selectByidCard(id_card)!=0){
            throw new CustomException("身份证号码重复，请重新确认！");
        }
        if (!sex.contains("男")&&!sex.contains("女")){
            throw new CustomException("性别错误，请重新选择！");
        }
        if (!id_type.contains("身份证")&&!id_type.contains("士官证")){
            throw new CustomException("证件选择错误，请重新选择！");
        }
        if (!phone.matches("^(130|131|132|133|153|155|134|135|136|137|138|139|150|151|152|156|157|158|159|180|181|182|183|187|188|189)\\d{8}$")){
            throw new CustomException("手机号码格式不正确，请重新尝试！");
        }
        if (!email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")){
            throw new CustomException("邮箱格式不正确，请重新填写！");
        }
        try {
            userService.insertUser(name, sex, pin_name, id_type, id_card, birthday, phone, email);
        } catch (CustomException | ParseException e) {
            log.info(e.getMessage());
            return Result.fail(100,e.getMessage());
        } catch (org.springframework.web.method.annotation.MethodArgumentTypeMismatchException e) {
            throw new CustomException("日期有误");
        }

        return Result.success(null);
    }

    /**
     * 查询全部用户信息
     * @return param<List> s
     */
    @RequestMapping("/selectall")
    public List<User> selectAll(){
        List<User> s = userService.selectAll();
        return s;
    }

    /**
     * 根据用户Id进行查询
     * @param id
     * @return
     */
    @GetMapping("/selectById")
    public Result<User> selectById(Integer id){
        List<User> list = userService.selectById(id);
        return Result.success(list);
    }

    /**
     * 更新用户数据
     * @param id
     * @param name
     * @param sex
     * @param pin_name
     * @param id_type
     * @param id_card
     * @param birthday
     * @param phone
     * @param email
     */
    @PostMapping("/update/{id}")
    public Result update(@PathVariable Integer id, @RequestParam String name,  @RequestParam String sex, @RequestParam String pin_name, @RequestParam String id_type, @RequestParam String id_card, @RequestParam Date birthday, @Valid @RequestParam String phone , @RequestParam String email){
        if (!name.matches("^([\\u4e00-\\u9fa5]{2,20}|[a-zA-Z.\\s]{2,20})$")){
            throw new CustomException("名字格式不正确，请重新填写！");
        }
        if (!pin_name.matches("^([\\u4e00-\\u9fa5]{2,20}|[a-zA-Z.\\s]{2,20})$")){
            throw new CustomException("名字拼写格式不正确，请重新填写！");
        }
        if (IDCardUtil.idCardValidate(id_card)==false){
            throw new CustomException("身份证号码有误，请重新输入！");
        }
        if (userService.selectByidCard(id_card)!=1&&userService.selectByidCard(id_card)!=0){
            throw new CustomException("身份证号码重复，请重新确认！");
        }
        if (!sex.contains("男")&&!sex.contains("女")){
            throw new CustomException("性别错误，请重新选择！");
        }
        if (!id_type.contains("身份证")&&!id_type.contains("士官证")){
            throw new CustomException("证件选择错误，请重新选择！");
        }
        if (!email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")){
            throw new CustomException("邮箱格式不正确，请重新填写！");
        }
        if (!phone.matches("^(130|131|132|133|153|155|134|135|136|137|138|139|150|151|152|156|157|158|159|180|181|182|183|187|188|189)\\d{8}$")){
            throw new CustomException("手机号码格式不正确，请重新尝试！");
        }
        try {
            userService.updateUser(id, name, sex, pin_name, id_type, id_card, birthday, phone, email);
        } catch (org.springframework.web.method.annotation.MethodArgumentTypeMismatchException e) {
            throw new CustomException("日期有误");
        }
        return Result.success(null);
    }

    /**
     * 删除用户
     * @param id
     */
    @RequestMapping ("/delete")
    public Result delete(@RequestParam Integer id){
            userService.deleteUser(id);
            return Result.success(null);


    }

    /**
     * 分页功能
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/page")
    public Page page(@RequestParam int page,@RequestParam int rows){
//        List s = userService.selectpage(page-1,rows);
        page=page-1;
        Page p = userService.selectpage(page, rows);
        return p;
    }
    @RequestMapping("/validate")
    public Boolean validate(@PathVariable String name){
        if (name=="2"){
            return true;
        }
        return false;
    }

    @RequestMapping("/idcard")
    public Result idcard(@RequestParam String idcard){
        int b = userService.selectByidCard(idcard);
        if(b>0){
            throw new CustomException("身份证号码已存在，请确认输入正确");
        }else {
            return Result.success(null);
        }
    }
}
