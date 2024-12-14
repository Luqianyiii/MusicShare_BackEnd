package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.mapper.NotificationMapper;
import com.hahaha.musicshare.model.entity.Notification;
import com.hahaha.musicshare.model.vo.NotificationVO;
import com.hahaha.musicshare.service.NotificationService;

import java.util.List;

public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
    @Override
    public List<NotificationVO> getNotificationByUserId(Integer user_id) {
        return List.of();
    }

    @Override
    public void deleteNotificationById(Integer notification_id) {

    }
}
