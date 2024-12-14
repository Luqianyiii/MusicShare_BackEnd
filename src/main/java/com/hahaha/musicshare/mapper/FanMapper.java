package com.hahaha.musicshare.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.musicshare.model.entity.Fan;

public interface FanMapper extends BaseMapper<Fan> {
    default Fan getById(Integer id) {
        return this.selectOne(new LambdaQueryWrapper<Fan>().eq(Fan::getId, id));
    }

    default Fan getByUserId(Integer userId) {
        return this.selectOne(new LambdaQueryWrapper<Fan>().eq(Fan::getFollowed_id, userId));
    }
    default Fan getByFanId(Integer fanId) {
        return this.selectOne(new LambdaQueryWrapper<Fan>().eq(Fan::getFan_id, fanId));
    }
}
