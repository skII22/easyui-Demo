package com.example.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.FileNotFoundException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class  GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result exceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.info(ex.getMessage());
        log.error(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return Result.fail(100, msg);
        }
        if (ex.getMessage().contains("Date")) {
            String msg = "日期有误";
            return Result.fail(100, msg);
        }
        return Result.fail(100,"未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public Result<String> exceptionHandler(CustomException ex){
//        log.error(ex.getMessage());
        return Result.fail(100,ex.getMessage());
    }


//    @ExceptionHandler(FileNotFoundException.class)
//    public Result<String> exceptionHandler(FileNotFoundException ex){
//        return Result.fail(100,"另一个程序正在使用此文件，进程无法访问。");
//    }
//    @ExceptionHandler(Exception.class )
//    public Result ExceptionHandler(Exception e){
//        return Result.fail(100,"未知错误");
//    }


}
