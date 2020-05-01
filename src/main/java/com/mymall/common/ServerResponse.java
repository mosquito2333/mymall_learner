package com.mymall.common;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化Json时，null的对象，key也会消失
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msq;
    private T data;

    /*私有构造器*/
    private ServerResponse(int status){
        this.status=status;
    }
    private  ServerResponse(int status,T data){
        this.status=status;
        this.data=data;
    }

    private ServerResponse(int status,String msq,T data){
        this.status=status;
        this.msq=msq;
        this.data=data;
    }

    private ServerResponse(int status,String msq){
        this.status=status;
        this.msq=msq;

    }
    /*其他函数*/
    @JsonIgnore
    //使之不在Json序列化结果当中
    public boolean isSuccess(){
        return this.status==ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsq() {
        return msq;
    }

    public T getData() {
        return data;
    }

    public static <T> ServerResponse<T> creatBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static  <T> ServerResponse<T> creatBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static  <T> ServerResponse<T> creatBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }
    public static  <T> ServerResponse<T> creatBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServerResponse<T> creatByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> creatByErrorMessage(String errorMassage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMassage);
    }

    public static <T> ServerResponse<T> creatByErrorCodeMessage(int errorCode,String errorMassage){
        return new ServerResponse<T>(errorCode,errorMassage);
    }


}
