package com.mymall.dao;

import com.mymall.pojo.Shipping;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer shoppingid);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer shoppingid);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}