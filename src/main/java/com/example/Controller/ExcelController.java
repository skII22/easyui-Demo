package com.example.Controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.example.Entity.User;
import com.example.Service.UserService;
import com.example.Util.*;
import com.example.common.CustomException;
import com.example.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    UserService userService;

    /**
     * Excel导入功能
     * @param multipartFile
     * @return Result
     * @throws IOException
     */
    @RequestMapping("/import")
    public Result excel(@RequestParam("excel") MultipartFile multipartFile) throws Exception {

        File file = new File("1.xlsx");
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);

        List list = new ArrayList<>();
        EasyExcel.read(file, User.class, new UserDataListener() {
            int i =0;
            List l = new ArrayList<>();
            // 每读取一行就调用该方法
            @Override
            public void invoke(User user, AnalysisContext analysisContext) {
                i++;


                    User u =new User();
                    if (!user.equals(u)) {
                        System.out.println(true);
                        if (userService.selectByidCard(user.getId_card()) != 0) {
                            l.add(i);
                        } else {
                            log.info(String.valueOf(user));
                            userService.insertUser(user);
                        }
                    }

            }


            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                List l1 = new ArrayList<>();
                l1=this.l;
                if (l1.size()!=0){
                    throw new CustomException("第"+l1.toString()+"行的身份证号码重复");
                }

            }
            // 全部读取完成就调用该方法
        }).registerConverter(new TimestampConverter()).registerConverter(new DateConverter()).sheet().doRead();

        if (file.exists()){
            file.delete();
        }
        EasyExcel.read().autoCloseStream(true);
        return Result.success(null);
    }

    /**
     * Excel导出功能
     * @return Result
     */
    @RequestMapping("/export")
    public void write(HttpServletResponse response) throws IOException {
        List<User> list = userService.selectAll();
//        String classpath = ResourceUtils.getURL("classpath:").getPath();
//        String path = ResourceUtils.getURL("src/main/resources/static/file/").getPath();
//        String filename = path+"/"+"导出信息.xls";

//        EasyExcel.write(filename,User.class).autoTrim(true).excludeColumnFiledNames(Collections.singleton("id")).sheet().doWrite(list);
//        EasyExcel.read().autoCloseStream(true);
        String fileName = new String("用户信息.xlsx".getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("application/x-xls");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName );
        EasyExcel.write(response.getOutputStream(),User.class).autoTrim(true).excludeColumnFiledNames(Collections.singleton("id")).sheet().doWrite(list);
//        return Result.success(null);

    }

    /**
     * 导出Excel空模板
     * @return Result
     */
    @RequestMapping("/temp")
    public Result temp() throws FileNotFoundException {
//        String classpath = ResourceUtils.getURL("classpath:").getPath();
        String path = ResourceUtils.getURL("src/main/resources/static/file/").getPath();
        String filename = path+"/"+"模板.xlsx";

        List list1 = null;
        List l = new ArrayList();
        l.add("id");
        l.add("updatetime");
        l.add("createtime");
        EasyExcel.write(filename,User.class).sheet().excludeColumnFiledNames(l).doWrite(list1);
        return Result.success(null);
    }
}

