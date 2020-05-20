package com.mymall.dao;

import com.mymall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer cartid);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer cartid);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
    Cart selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
    List<Cart> selectCartByUserId(Integer userId);
    //未选中商品项数
    int selectCartProductCheckedStatusByUserId(Integer userId);
    //根据有用户编号和商品编号们删除购物车中的购物项们
    int deleteByUserIdProductsIds(@Param("userId") Integer userId,@Param("productIdList") List<String> productIdList);

    int checkedOrUncheckedProduct(@Param("userId")Integer userId,@Param("productId") Integer productId,@Param("checked") Integer checked);

    int getCartProductCount(@Param("userId")Integer userId);
}