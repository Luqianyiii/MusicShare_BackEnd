package com.hahaha.musicshare.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.musicshare.model.entity.notification;

public interface NotificationMapper extends BaseMapper<notification> {
    default notification getById(Integer id) {
        return this.selectOne(new LambdaQueryWrapper<notification>().eq(notification::getId, id));
    }
}
