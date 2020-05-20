package com.mymall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mymall.common.ServerResponse;
import com.mymall.dao.ShoppingMapper;
import com.mymall.pojo.Shopping;
import com.mymall.service.IShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("iShoppingService")
public class ShoppingServiceImpl implements IShoppingService {

    @Autowired
    private ShoppingMapper shoppingMapper;

    public ServerResponse add(Integer userId,Shopping shopping){
        shopping.setUserId(userId);
        int rowCount= shoppingMapper.insert(shopping);
        if(rowCount>0){
            Map result = Maps.newHashMap();
            result.put("shoppingId",shopping.getShoppingid());
            return ServerResponse.creatBySuccess("新建地址成功",result);
        }
        return ServerResponse.creatByErrorMessage("新建地址失败");
    }

    public ServerResponse<String> delete(Integer userId, Integer shoppingId){
        int resultCount=shoppingMapper.deleteByPrimaryKey(shoppingId);
        if(resultCount>0){
            return ServerResponse.creatBySuccess("删除地址成功");
        }
        return ServerResponse.creatByErrorMessage("删除地址失败");
    }

    public ServerResponse update(Integer userId,Shopping shopping){
        shopping.setUserId(userId);
        int rowCount= shoppingMapper.updateByShipping(shopping);
        if(rowCount>0){
            return ServerResponse.creatBySuccess("更新地址成功");
        }
        return ServerResponse.creatByErrorMessage("更新地址失败");
    }

    public ServerResponse<Shopping> select(Integer userId,Integer shoppingId){
        Shopping shopping= shoppingMapper.selectByShoppingIdUserId(userId,shoppingId);
        if(shopping!=null){
            return ServerResponse.creatBySuccess("查询地址成功",shopping);
        }
        return ServerResponse.creatByErrorMessage("查询地址失败");
    }

    public ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Shopping> shoppingList=shoppingMapper.selectByUserId(userId);
        PageInfo pageInfo=new PageInfo(shoppingList);
        return ServerResponse.creatBySuccess(pageInfo);
    }


}
