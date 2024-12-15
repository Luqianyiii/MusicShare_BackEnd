package com.hahaha.musicshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.musicshare.model.dto.UserEditDTO;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserInfoVO;

public interface UserService extends IService<User> {

     /**
     * ⽤户信息
     *
     * @return {@link UserInfoVO}
     */
    UserInfoVO userInfo();

    /**
     * 更新信息
     *
     * @param userEditDTO ⽤户编辑 DTO
     * @return UserInfoVO
     */

    UserInfoVO updateInfo(UserEditDTO userEditDTO);


}