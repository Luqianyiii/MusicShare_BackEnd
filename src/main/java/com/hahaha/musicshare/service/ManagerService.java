package com.hahaha.musicshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserInfoVO;
import com.hahaha.musicshare.model.vo.UserLoginVO;

import java.util.List;

public interface ManagerService extends IService<User> {

    //获取所有用户信息
    List<UserInfoVO> getAllUser();

    //删除用户
    void deleteUser(Integer user_id);

    //根据关键词搜索用户
    List<UserInfoVO> getUserByKeyword();
}
