package com.mymall.common;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCache {
    public static final String TOKEN_PREFIX ="token_" ;
    private static Logger logger= LoggerFactory.getLogger(TokenCache.class);
    //缓存初始化容量1000，缓存最大容量10000 超出次最大容量则采用LRU算法。这两个参数声明顺序可互换
    //有效期12hour
    private static LoadingCache<String,String> localCache= CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS).build(
                    new CacheLoader<String, String>() {
                        //默认数据加载实现，调用取值时，若没有命中的key值，则默认调用此方法加载
                        @Override
                        public String load(String s) throws Exception {
                            return null;
                        }
                    }
            );

    public static void setKey(String key,String value){
        localCache.put(key,value);
    }
    public static String getKey(String key){
        String value=null;
        try{
            value=localCache.get(key);
            if("null".equals(value)){
                return null;
            }
            return value;
        }catch (Exception e){
            logger.error("localCache get error",e);
        }
        return null;
    }
}
