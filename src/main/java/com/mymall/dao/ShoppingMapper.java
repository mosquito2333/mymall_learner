package com.mymall.dao;

import com.mymall.pojo.Shopping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingMapper {
    int deleteByPrimaryKey(Integer shoppingid);

    int insert(Shopping record);

    int insertSelective(Shopping record);

    Shopping selectByPrimaryKey(Integer shoppingid);

    int updateByPrimaryKeySelective(Shopping record);

    int updateByPrimaryKey(Shopping record);

    int deleteByShoppingIdUserId(@Param("userId") Integer userId, @Param("shoppingId") Integer shopppingId);
    int updateByShipping(Shopping record);
    Shopping selectByShoppingIdUserId(@Param("userId") Integer userId, @Param("shoppingId") Integer shopppingId);
    List<Shopping> selectByUserId(@Param("userId") Integer userId);

}