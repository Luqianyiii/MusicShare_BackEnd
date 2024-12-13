package com.hahaha.musicshare.service;

import org.springframework.web.multipart.MultipartFile;

public interface CommunicationService {
    /**
     * 发送短信
     *
     * @param phone ⼿机号
     */
    void sendSms(String phone);


    /**
     * 绑定手机号
     *
     * @param phone    手机号
     * @param code     验证码
     * @param accessToken accessToken
     */

    void bindPhone(String phone, String code, String accessToken);

    /**
     * 上传头像
     *
     * @param file ⽂件
     * @return {@link String}
     */
    String uploadAvatar(MultipartFile file);

    String updatePassword(String phone,String code,String password,String accessToken);
}
