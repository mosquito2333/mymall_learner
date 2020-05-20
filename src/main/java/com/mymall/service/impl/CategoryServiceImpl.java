package com.mymall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mymall.common.ServerResponse;
import com.mymall.dao.CategoryMapper;
import com.mymall.pojo.Category;
import com.mymall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

//向上注入，至ICategoryService接口之中
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;

    //添加品类
    public ServerResponse addCategory(String categoryName,Integer parentId){
        if(parentId==null|| StringUtils.isBlank(categoryName)){
            return ServerResponse.creatByErrorMessage("添加品类参数错误");
        }
        Category category=new Category();
        category.setCatename(categoryName);
        category.setParentid(parentId);
        category.setCatestatus(true);//当前分类是可用的

        int rowCount =categoryMapper.insert(category);
        if(rowCount>0){
            return ServerResponse.creatBySuccessMessage("添加品类成功");
        }
        return ServerResponse.creatByErrorMessage("添加品类失败");
    }

    //设置品类名称
    public ServerResponse updateCategoryName(Integer categoryId,String categoryName){

        if(categoryId==null|| StringUtils.isBlank(categoryName)){
            return ServerResponse.creatByErrorMessage("更新品类参数错误");
        }
        Category category=new Category();
        category.setCatename(categoryName);
        category.setCateid(categoryId);
        int rowCount =categoryMapper.updateByPrimaryKey(category);
        if(rowCount>0){
            return ServerResponse.creatBySuccessMessage("更新品类成功");
        }
        return ServerResponse.creatByErrorMessage("更新品类失败");
    }

    public ServerResponse<List<Category>> getChildrenParallel(Integer categoryId){
        if(categoryId==null){
            return ServerResponse.creatByErrorMessage("查询当前品类子类参数错误");
        }
        List<Category> rowCountList=categoryMapper.selectChildrenParallel(categoryId);
        if(CollectionUtils.isEmpty(rowCountList)){
            logger.info("未找到当前子类分类");
        }
        return ServerResponse.creatBySuccess(rowCountList);
    }

    //递归查询本节点id及孩子节点id
    public ServerResponse<List<Integer>> getChildrenDeep(Integer categoryId){
        Set<Category> categorySet= Sets.newHashSet();
        findChildrenCategory(categorySet,categoryId);

        List<Integer> categoryList= Lists.newArrayList();
        if(categoryId!=null){
            for(Category categoryItem:categorySet){
                categoryList.add(categoryItem.getCateid());
            }
        }
        return ServerResponse.creatBySuccess(categoryList);
    }

    //递归算法，算出子节点
    private Set<Category> findChildrenCategory(Set<Category> categorySet,Integer categoryId){
        Category category=categoryMapper.selectByPrimaryKey(categoryId);
        if(category!=null){
            categorySet.add(category);
        }
        //查找子节点，递归算法一定要有一个退出的条件
        List<Category> categoryList=categoryMapper.selectChildrenParallel(categoryId);
        for(Category categoryItem :categoryList){
            findChildrenCategory(categorySet,categoryItem.getCateid());
        }
        return categorySet;
    }

}
