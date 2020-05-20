package com.mymall.controller.backend;

import com.mymall.common.Const;
import com.mymall.common.ResponseCode;
import com.mymall.common.ServerResponse;
import com.mymall.pojo.User;
import com.mymall.service.ICategoryService;
import com.mymall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    //session为校验当前用户是否为管理员时，用到IUserService这个接口
    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;
    //父节点

    @RequestMapping(value="add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value="parentId",defaultValue = "0") int parentId){
        //判断当前登录用户的类型
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录请登录");
        }
        //校验是否为管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //是管理员，处理分类逻辑
            return iCategoryService.addCategory(categoryName,parentId);
        }
        else{
            return ServerResponse.creatByErrorMessage("无权限操作，管理员才能操作");
        }
    }

    @RequestMapping(value="set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        //判断当前登录用户的类型
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录请登录");
        }
        //校验是否为管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //是管理员，处理更新品类信息逻辑
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        }
        else{
            return ServerResponse.creatByErrorMessage("无权限操作，管理员才能操作");
        }
    }

    @RequestMapping(value="get_children_parallel.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value="categoryId",defaultValue = "0") Integer categoryId){

        //判断当前登录用户的类型
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录请登录");
        }
        //校验是否为管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //查询子节点的Category信息，并且不递归，保持平级
            return iCategoryService.getChildrenParallel(categoryId);
        }
        else{
            return ServerResponse.creatByErrorMessage("无权限操作，管理员才能操作");
        }

    }

    @RequestMapping(value="get_children_deep.do")
    @ResponseBody
    public ServerResponse getChildrenDeepCategory(HttpSession session,@RequestParam(value="categoryId",defaultValue = "0") Integer categoryId){
        //判断当前登录用户的类型
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录请登录");
        }
        //校验是否为管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //查询子节点的Category信息，递归查询
            return iCategoryService.getChildrenDeep(categoryId);
        }
        else{
            return ServerResponse.creatByErrorMessage("无权限操作，管理员才能操作");
        }
    }



}
