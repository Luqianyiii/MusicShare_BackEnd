package com.hahaha.musicshare.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.hahaha.musicshare.model.entity.Notification;

import java.util.List;
public interface NotificationMapper extends MPJBaseMapper<Notification> {
    @SuppressWarnings({"rawtypes", "unchecked"})
    default List<Notification> getByUserId(Integer userId) {
        return this.selectList(new LambdaQueryWrapper<Notification>().eq(Notification::getRecipient_id, userId));
    }
}
