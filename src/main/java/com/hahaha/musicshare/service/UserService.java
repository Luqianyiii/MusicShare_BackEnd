package com.hahaha.musicshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.musicshare.model.dto.UserEditDTO;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 上传头像
     *
     * @param file ⽂件
     * @return {@link String}
     */
    String uploadAvatar(MultipartFile file);

    String updatePassword(String phone,String code,String password);
}