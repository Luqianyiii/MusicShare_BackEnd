package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.mapper.NotificationMapper;
import com.hahaha.musicshare.model.entity.Notification;
import com.hahaha.musicshare.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
    @Override
    public List<Notification> getNotificationByUserId() {
        return baseMapper.getByUserId(RequestContext.getUserId());
    }

    @Override
    public void deleteNotificationById(Integer notification_id) {
        baseMapper.deleteById(notification_id);
    }

    @Override
    public void addNotification(Integer recipient, String inform) {
        Notification notification = new Notification();
        notification.setContent(inform);
        notification.setRecipient_id(recipient);
        baseMapper.insert(notification);
    }
}
