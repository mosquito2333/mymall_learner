package com.mymall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mymall.common.Const;
import com.mymall.common.ResponseCode;
import com.mymall.common.ServerResponse;
import com.mymall.dao.CategoryMapper;
import com.mymall.dao.ProductMapper;
import com.mymall.pojo.Category;
import com.mymall.pojo.Product;
import com.mymall.service.ICategoryService;
import com.mymall.service.IProductService;
import com.mymall.util.DateTimeUtil;
import com.mymall.util.PropertiesUtil;
import com.mymall.vo.ProductDetailVo;
import com.mymall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    public ServerResponse saveOrUpdateProduct(Product product){
        if(product!=null){
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray=product.getSubImages().split(",");
                if(subImageArray.length>0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            if(product.getProdid()!=null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {
                    return ServerResponse.creatBySuccess("更新产品成功");
                }
                return ServerResponse.creatBySuccess("更新产品失败");
            }else{
                int rowCount=productMapper.insert(product);
                if(rowCount>0){
                    return ServerResponse.creatBySuccess("新增产品成功");
                }
                return ServerResponse.creatBySuccess("新增产品失败");
            }
        }
        return ServerResponse.creatByErrorMessage("新增或更新产品参数错误");
    }
    public ServerResponse<String> setSaleStatus(Integer productId,Integer productStatus){
        if(productId==null || productStatus==null){
            return ServerResponse.creatByErrorMessage("产品编号productId和要设置的productStatus都不允许为空；");
            //return ServerResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product =new Product();
        product.setProdid(productId);
        product.setProdstatus(productStatus);
        int rowCount=productMapper.updateByPrimaryKeySelective(product);
        if(rowCount>0){
            return ServerResponse.creatBySuccessMessage("修改产品销售状态成功");
        }
        return ServerResponse.creatByErrorMessage("修改产品销售状态失败");
    }


    public ServerResponse<ProductDetailVo> getDetail(Integer productId){
        if(productId==null){
            return ServerResponse.creatByErrorMessage("产品编号productId不允许为空；");
            //return ServerResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.creatByErrorMessage("此产品下架或删除");
        }
        //VO对象---value object
        //pojo->bo(business object)->vo(view object)
        ProductDetailVo productDetailVo=assemleProductDetailVo(product);
        return ServerResponse.creatBySuccess(productDetailVo);
    }

    private  ProductDetailVo assemleProductDetailVo(Product product){
        ProductDetailVo productDetailVo=new ProductDetailVo();
        productDetailVo.setProdid(product.getProdid());
        productDetailVo.setCateid(product.getCateid());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setProdname(product.getProdname());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setProdstatus(product.getProdstatus());
        productDetailVo.setStock(product.getStock());

        //imageHost
        //parentCategoryId
        //createTime
        //updateTime
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://image.mymall.com/"));
        Category category =categoryMapper.selectByPrimaryKey(product.getCateid());
        if(category==null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }
        else{
            productDetailVo.setParentCategoryId(category.getParentid());
        }
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    //分页
    public ServerResponse<PageInfo> getProductList(int pageNum,int pageSize){
        //startPage--start
        PageHelper.startPage(pageNum,pageSize);
        //填充自己的sql查询逻辑,添加到Mapper中
            //存放所有产品
        List<Product> productList=productMapper.selectList();
            //存放产品产生的productListVo
        List<ProductListVo> productListVoList= Lists.newArrayList();
        for(Product productItem:productList){
            ProductListVo productListVo=assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        //pageHelper--end
        PageInfo pageResult=new PageInfo(productList);
        pageResult.setList(productList);
        return ServerResponse.creatBySuccess(pageResult);
    }

    //初加工product，加路径之类的，感觉本质也是product
    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo=new ProductListVo();
        productListVo.setProdid(product.getProdid());
        productListVo.setCateid(product.getCateid());
        productListVo.setProdname(product.getProdname());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://image.mymall.com/"));

        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setProdstatus(product.getProdstatus());
        return productListVo;
    }

    //搜索产品的逻辑实现
    public ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize){
        //startPage--start
        // 填充自己的sql查询逻辑,添加到Mapper中
        //pageHelper--end
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName=new StringBuilder().append("%").append(productName).append("%").toString();
        }
        //填充自己的sql查询逻辑,添加到Mapper中
        List<Product> productList=productMapper.selectByNameAndProductId(productName,productId);
        List<ProductListVo> productListVoList= Lists.newArrayList();
        for(Product productItem : productList){
            ProductListVo productListVo=assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult=new PageInfo(productList);
        pageResult.setList(productList);
        return ServerResponse.creatBySuccess(pageResult);
    }

    //获取产品详情的逻辑实现
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if(productId==null){
            return ServerResponse.creatByErrorMessage("产品编号productId不允许为空；；");
            //return ServerResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.creatByErrorMessage("此产品下架或删除");
        }
        if(product.getProdstatus()!= Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.creatByErrorMessage("此产品下架或删除");
        }
        ProductDetailVo productDetailVo=assemleProductDetailVo(product);
        return ServerResponse.creatBySuccess(productDetailVo);
    }

    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy){
        if(StringUtils.isBlank(keyword)&&categoryId==null){
            return ServerResponse.creatByErrorMessage("产品名关键字keyword和产品编号categoryId都不允许为空；");
            //return ServerResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList=new ArrayList<Integer>();
        if(categoryId!=null){
            Category category=categoryMapper.selectByPrimaryKey(categoryId);
            if(category==null&&StringUtils.isBlank(keyword)){
                //没有分类，且没有categoryId，返回空结果值，不报错
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList=Lists.newArrayList();
                PageInfo pageInfo=new PageInfo(productListVoList);
                return ServerResponse.creatBySuccess(pageInfo);
            }
            categoryIdList = iCategoryService.getChildrenDeep(category.getCateid()).getData();
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword=new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum,pageSize);
        //排序处理
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray=orderBy.split("_");
                //orderBy参数格式为price desc
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
             }
        }
        //categoryIdList.size()==0?null:categoryIdList
        List<Product> productList =productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,categoryIdList.isEmpty()?null:categoryIdList);

        List<ProductListVo> productListVoList=Lists.newArrayList();
        for(Product product:productList){
            ProductListVo productListVo=assembleProductListVo(product);
            productListVoList.add(productListVo);
        }

        PageInfo pageInfo=new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.creatBySuccess(pageInfo);
    }
}
