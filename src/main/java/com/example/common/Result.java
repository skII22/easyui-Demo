package com.example.common;

import lombok.Data;
/**
 * 通用返回结果，服务端响应的数据最终都会封装成此对象
 * @param <T>
 */
@Data
public class Result<T> {
    private boolean success;

    private int code;

//    private String msg;
     private String errorMsg;

    private Object data;


    public Result(boolean b, int code, String errorMsg, Object o) {
        this.success=b;
        this.code=code;
        this.errorMsg=errorMsg;
        this.data=o;
    }


    public static Result success(Object data) {
        return new Result(true, 200,null, data);
    }

    public static Result fail(int code, String errorMsg) {
        return new Result(false, code, errorMsg, null);
    }
}