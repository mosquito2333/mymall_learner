package com.mymall.dao;

import com.mymall.pojo.Category;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer cateid);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer cateid);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}