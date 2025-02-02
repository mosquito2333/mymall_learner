package com.mymall.dao;

import com.mymall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer prodid);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer prodid);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();
    List<Product> selectByNameAndProductId(@Param("productName") String productName, @Param("productId") Integer productId);
    List<Product> selectByNameAndCategoryIds(@Param("productName") String productName,@Param("categoryIdList") List<Integer> categoryIdList);


}