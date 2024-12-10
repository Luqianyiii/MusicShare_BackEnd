package com.hahaha.musicshare.service;

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
}
