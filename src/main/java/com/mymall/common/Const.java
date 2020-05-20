package com.mymall.common;

import com.google.common.collect.Sets;

import java.util.Set;

public class Const {
    //静态常量，可通过类名直接调用
    public static final String CURRENT_USER="currentUser";

    public static final String Email="email";
    public static final String Username="username";

    public interface ProductListOrderBy{
        //所用字段为price，升序 asc，降序 desc，set.contains()时间复杂度为O(1)
        Set<String> PRICE_ASC_DESC= Sets.newHashSet("price_desc","price_asc");
    }

    public interface Cart{
        int CHECKED=1;//即购物车选中状态
        int UN_CHECKED=0;//购物车为选中状态

        String LIMIT_NUM_FAIT="LIMIT_NUM_FAIT";
        String LIMTE_NUM_SUCCESS="LIMTE_NUM_SUCCESS";
    }
    public interface Role{
        int ROLE_CUSTOMER=0;//普通用户
        int ROLE_ADMIN=1;//管理员用户
    }
    public enum ProductStatusEnum{

        ON_SALE(1,"在线");
        private int code;
        private String value;

        ProductStatusEnum(int code,String value){
            this.code=code;
            this.value=value;
        }
        public int getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }
    }
}
