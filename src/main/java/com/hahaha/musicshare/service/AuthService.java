package com.hahaha.musicshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserLoginVO;

public interface AuthService extends IService<User> {
    /**
     * 登录
     *
     * @param phone 电话
     * @param code  验证码
     * @return {@link UserLoginVO}
     */
    UserLoginVO loginByPhone(String phone, String code);


    /**
     * 登出
     */
    void logout();

    /**
     * 绑定⼿机号
     *
     * @param phone       电话
     * @param code        验证码
     * @param accessToken 访问令牌
     */
    void bindPhone(String phone, String code, String accessToken);
}
