package com.wjysky.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: DataApi
 * @Description: 用于基本数据传递的实体
 * @Author: 王俊元（wangjunyuan@newhow.com.cn）
 * @Date: 2019-03-02 1:37
 * @Version 1.0
 */
@Getter
@Setter
public class DataApi<T> {

    private boolean result; // 返回结果

    private int code; // 返回码

    private String msg; // 返回说明

    private T data; // 附加的通用值

    private long systemTime; // 当前系统时间戳

    public static <T> DataApi<T> generateFailMsg(int code, String msg){
        DataApi api = new DataApi();
        api.setResult(false);
        api.setCode(code);
        api.setMsg(msg);
        api.setSystemTime(System.currentTimeMillis());
        return api;
    }

    public static <T> DataApi<T> generateSuccessMsg(){
        DataApi api = new DataApi();
        api.setResult(true);
        api.setCode(0);
        api.setMsg("处理成功");
        api.setSystemTime(System.currentTimeMillis());
        return api;
    }

    public static <T> DataApi<T> generateSuccessMsg(String msg){
        DataApi api = new DataApi();
        api.setResult(true);
        api.setCode(0);
        api.setMsg(msg);
        api.setSystemTime(System.currentTimeMillis());
        return api;
    }

    public static <T> DataApi<T> generateFailMsg(int code, String msg, T data){
        DataApi api = new DataApi();
        api.setResult(false);
        api.setCode(code);
        api.setMsg(msg);
        api.setData(data);
        api.setSystemTime(System.currentTimeMillis());
        return api;
    }

    public static <T> DataApi<T> generateSuccessMsg(int code, String msg){
        DataApi api = new DataApi();
        api.setResult(true);
        api.setCode(code);
        api.setMsg(msg);
        api.setSystemTime(System.currentTimeMillis());
        return api;
    }

    public static <T> DataApi<T> generateSuccessMsg(int code, String msg, T data){
        DataApi api = new DataApi();
        api.setResult(true);
        api.setCode(code);
        api.setMsg(msg);
        api.setData(data);
        api.setSystemTime(System.currentTimeMillis());
        return api;
    }

    public static <T> DataApi<T> generateExceptionMsg(){
        DataApi api = new DataApi();
        api.setResult(false);
        api.setCode(-9999);
        api.setMsg("后台繁忙，请稍后重试");
        api.setSystemTime(System.currentTimeMillis());
        return api;
    }

    public static <T> DataApi<T> generateExceptionMsg(String msg){
        DataApi api = new DataApi();
        api.setResult(false);
        api.setCode(-9999);
        api.setMsg(msg);
        api.setSystemTime(System.currentTimeMillis());
        return api;
    }
}
