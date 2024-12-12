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

    UserLoginVO loginByCode(String phone, String code);


     /**
     * 密码登录
     *
     * @param phone    电话
     * @param password 密码
     * @return {@link UserLoginVO}
     */

    UserLoginVO loginByPassword(String phone,String password);


    /**
     * 登出
     */
    void logout();


}
