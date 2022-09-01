package com.example.Entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.Util.DateConverter;
import com.example.Util.TimestampConverter;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import java.sql.Timestamp;
import java.util.Date;
@Data
@Entity
@Table(name = "physicaldiagram")
//@DynamicUpdate(true)
public class User {


    @ExcelProperty(value = {"ID"})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;



    /*姓名*/

    @ExcelProperty(value = {"姓名"})
    @Column(name = "name")
    String name;


    /*姓名全拼*/

    @ExcelProperty(value = {"姓名全拼"})
    @Column(name = "pin_name")
    String pin_name;


    @Pattern(regexp = "^(130|131|132|133|153|155|134|135|136|137|138|139|150|151|152|156|157|158|159|180|181|182|183|187|188|189)\\d{8}$",message = "手机号码格式错误")
    @ExcelProperty(value = {"手机号码"})
    /*手机号码*/
    @Column(name = "phone")
    String phone;


    @Email(message = "请输入正确的邮箱")
    @ExcelProperty(value = {"电子邮箱"})
    /*电子邮箱*/
    @Column(name = "email")
    String email;

    @Size(min = 1,max = 1)
    @Pattern(regexp = "男|女",message = "请选择正确的性别！")
    @ExcelProperty(value = {"性别"})
    /*性别*/
    @Column(name = "sex")
    String sex;

    @Size(min = 3,max = 5)
    @Pattern(regexp = "身份证|士官证",message = "请选择正确的证件类型！")
    @ExcelProperty(value = {"证件类型"})
    /*身份证类型*/
    @Column(name = "id_type")
    String id_type;


    @ExcelProperty(value = {"证件号码"})
    /*身份证号*/
    @Column(name = "id_card")
    String id_card;


    @ExcelProperty(value = {"出生日期"},converter = DateConverter.class)
    /*出生日期*/
    @Past(message ="请输入正确的日期格式！")
    @Column(name = "birthday")
    Date birthday;


    @ExcelProperty(value = {"创建时间"},converter = TimestampConverter.class)
    /*创建时间*/
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "createtime",updatable=false )
    Timestamp createtime;


    @ExcelProperty(value = {"修改时间"},converter = TimestampConverter.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /*修改时间*/
    @UpdateTimestamp
    @Column(name = "updatetime")
    Timestamp updatetime;

    public User() {
    }

    public User (String name, String pin_name, String sex, String id_type, String id_card, Timestamp birthday, String phone, String email) {
        this.id=null;
        this.name=name;
        this.birthday=birthday;
        this.sex=sex;
        this.pin_name=pin_name;
        this.id_type=id_type;
        this.id_card=id_card;
        this.phone=phone;
        this.email=email;
        this.createtime=null;
        this.updatetime=null;
    }

    public User(Integer id, String name, String pin_name, String phone, String email, String sex, String id_type, String id_card, Date birthday, Timestamp createtime, Timestamp updatetime) {
        this.id = id;
        this.name = name;
        this.pin_name = pin_name;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
        this.id_type = id_type;
        this.id_card = id_card;
        this.birthday = birthday;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }
}
