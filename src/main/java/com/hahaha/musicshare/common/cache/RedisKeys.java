package com.hahaha.musicshare.common.cache;

//处理传入字段，自定义Redis缓存存储规范
public class RedisKeys {
    /**
     * 验证码Key
     */
    public static String getSmsKey(String phone) {
        return "sms:captcha:" + phone;
    }
    /**
     * accessToken Key
     */
    public static String getAccessTokenKey(String accessToken) {
        return "sys:access:" + accessToken;
    }
    /**
     * 获取⽤户 ID 密钥
     *
     * @param id id
     * @return {@link String}
     */
    public static String getUserIdKey(Integer id) {
        return "sys:userId:" + id;
    }
}
