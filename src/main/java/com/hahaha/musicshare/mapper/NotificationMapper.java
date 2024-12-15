package com.hahaha.musicshare.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.musicshare.model.entity.Notification;

import java.util.List;

public interface NotificationMapper extends BaseMapper<Notification> {

    default List<Notification> getByUserId(Integer userId) {
        return this.selectList(new LambdaQueryWrapper<Notification>().eq(Notification::getRecipient_id, userId));
    }
}
