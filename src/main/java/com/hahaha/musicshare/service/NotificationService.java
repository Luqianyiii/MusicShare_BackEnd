package com.hahaha.musicshare.service;

import com.hahaha.musicshare.model.entity.Notification;

import java.util.List;

public interface NotificationService {

    //根据用户id获取通知
    List<Notification> getNotificationByUserId();

    //根据通知id删除通知
    void deleteNotificationById(Integer notification_id);

    void addNotification(Integer recipient,String inform);

}
