package com.mymall.service;

import com.mymall.common.ServerResponse;
import com.mymall.vo.CartVo;

public interface ICartService {

    ServerResponse<CartVo> list(Integer userId);
    ServerResponse add(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);
    ServerResponse<CartVo>  selectOrUnSelect(Integer userId,Integer productId,Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
