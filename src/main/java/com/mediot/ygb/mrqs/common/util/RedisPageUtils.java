package com.mediot.ygb.mrqs.common.util;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

public class RedisPageUtils {

    /**
     * 存放单个hash缓存
     * @param key 键
     * @param hkey 键
     * @param value 值
     * @return
     */
    public static boolean hput(RedisTemplate redisTemplate,Object key, Object hkey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hkey, value);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 分页存取数据
     * @param key  hash存取的key
     * @param hkey hash存取的hkey
     * @param score 指定字段排序
     * @param value
     * @return
     */
    public static boolean setPage(RedisTemplate redisTemplate,Object key, Object hkey, double score, Object value){
        boolean result = false;
        try {
            redisTemplate.opsForZSet().add(key, hkey, score);
            result = hput(redisTemplate,key, hkey, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 分页取出 hash中hkey值
     * @param key
     * @param offset
     * @param count
     * @return
     */
    public static Set<String> getPage(RedisTemplate redisTemplate,String key, int offset, int count){
        Set<String> result = null;
        try {
            result = redisTemplate.opsForZSet().range(key,1,3);
            //1 100000代表score的排序氛围值，即从1-100000的范围
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 计算key值对应的数量
     * @param key
     * @return
     */
    public static Integer getSize(RedisTemplate redisTemplate,String key){
        Integer num = 0;
        try {
            Long size = redisTemplate.opsForZSet().zCard(key);
            return size.intValue();
        } catch (Exception e) {
        }
        return num;
    }

}
