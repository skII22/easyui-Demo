package com.example.Mapping;

import com.example.Entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public interface UserMapper extends PagingAndSortingRepository<User,Integer> {
    Date date = new Date();
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
//    @Query(value = "UPDATE physicaldiagram SET name=name,sex=sex,id_type=id_type,id_card=id_card,phone=phone,email=email,pin_name=pin_name,birthday=birthday,updatetime = dateFormat  WHERE id =?1",nativeQuery = true)
//    public void updateById(Integer id,String name,String sex,String pin_name,String id_type,String id_card,String birthday,String phone ,String email);
    @Modifying
    @Transactional
    @Query("delete  from User WHERE id = ?1")
    public void deleteUser(Integer id);

    @Query("select id from User where name=name and pin_name=pin_name and sex=sex and id_type=id_type and id_card=id_card and birthday=birthday and phone=phone and email=email")
    public Long findId(String name,String pin_name,String sex,String id_type,String id_card,Date birthday,String phone,String email);
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "Insert into physicaldiagram (id,name,pin_name,sex,id_type,id_card,phone,email,birthday) values (#{id},#{name},#{pin_name},#{sex},#{id_type},#{id_card},#{phone},#{email},#{birthday})",nativeQuery = true)
    void insertUser(User user);
    @Modifying
    @Query("select id from User where id_card=?1")
    public List selectbyIdCard(String id_card);
}
