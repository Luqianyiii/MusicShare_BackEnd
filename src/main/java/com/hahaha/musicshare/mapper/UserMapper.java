package com.hahaha.musicshare.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.musicshare.model.entity.User;

public interface UserMapper extends BaseMapper<User> {
    default User getByPhone(String phone) {
        return this.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, phone));
    }
}
