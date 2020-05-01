package com.mymall.common;

public class Const {
    //静态常量，可通过类名直接调用
    public static final String CURRENT_USER="currentUser";

    public static final String Email="email";
    public static final String Username="username";

    public interface Role{
        int ROLE_CUSTOMER=0;//普通用户
        int ROLE_ADMIN=1;//管理员用户
    }
}
