package com.hahaha.musicshare.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.musicshare.model.entity.Notification;

public interface NotificationMapper extends BaseMapper<Notification> {
    default Notification getById(Integer id) {
        return this.selectOne(new LambdaQueryWrapper<Notification>().eq(Notification::getId, id));
    }

    default Notification getByUserId(Integer userId) {
        return this.selectOne(new LambdaQueryWrapper<Notification>().eq(Notification::getRecipient_id, userId));
    }
}
