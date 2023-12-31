package com.jack.LabSystem.util;

import lombok.Data;

/**
 * 返回结果的泛型类
 * @author hello,world
 * @date 2023/8/26 10:11
 */
@Data
public class ResultUtil<T> {
    //状态码的信息
    private Integer code;
    //错误信息
    private String message;
    //接收的数据
    private T data;

    /**
     * 请求成功时调用的基础方法
     * @param object
     * @param <T>
     * @return
     */
    public static <T> ResultUtil<T> success(T object){
        //创建一个结果对象
        ResultUtil<T> result =new ResultUtil<>();
        result.setCode(20000);
        result.setData(object);
        return result;
    }

    public static <T> ResultUtil<T> success(String message){
        //创建一个结果对象
        ResultUtil<T> result =new ResultUtil<>();
        result.setCode(20000);
        result.setMessage(message);
        return result;
    }
    /**
     * 失败时调用的失败的基础方法
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResultUtil<T> fail(String msg){
        //创建一个结果对象
        ResultUtil<T> result =new ResultUtil<>();
        result.setCode(-1);
        result.setMessage(msg);
        return result;
    }

    //重载
    public static <T> ResultUtil<T> fail(T data){
        //创建一个结果对象
        ResultUtil<T> result =new ResultUtil<>();
        result.setCode(-1);
        result.setData(data);
        return result;
    }
}
