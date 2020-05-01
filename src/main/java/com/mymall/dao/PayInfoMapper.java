package com.mymall.dao;

import com.mymall.pojo.PayInfo;

public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer payid);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer payid);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);
}