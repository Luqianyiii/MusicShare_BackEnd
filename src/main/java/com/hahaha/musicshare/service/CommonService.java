package com.hahaha.musicshare.service;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
    /**
     * 发送短信
     *
     * @param phone ⼿机号
     */
    void sendSms(String phone);

    String upload(MultipartFile file);
}
