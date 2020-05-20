package com.mymall.service;

import com.github.pagehelper.PageInfo;
import com.mymall.common.ServerResponse;
import com.mymall.pojo.Shopping;


public interface IShoppingService {
    ServerResponse add(Integer userId, Shopping shopping);
    ServerResponse delete(Integer userId, Integer shoppingId);
    ServerResponse update(Integer userId,Shopping shopping);
    ServerResponse<Shopping> select(Integer userId,Integer shoppingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);



}
