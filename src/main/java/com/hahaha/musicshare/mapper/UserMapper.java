package com.hahaha.musicshare.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hahaha.musicshare.convert.UserConvert;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserInfoVO;

import java.util.List;

public interface UserMapper extends MPJBaseMapper<User> {
    default User getByPhone(String phone) {
        return this.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, phone));
    }

    default List<UserInfoVO> getUsers() {
        MPJLambdaWrapper<User> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll()
                .eq(User::getRemark, "user");
        return UserConvert.INSTANCE.convert(this.selectList(wrapper));
    }

    //模糊搜索
    default List<UserInfoVO> SearchUsers(String keyword) {
        MPJLambdaWrapper<User> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll()
                .eq(User::getRemark, "user")
                .like(User::getPhone, keyword)
                .or()
                .like(User::getNickname, keyword)
                .or()
                .like(User::getMotto, keyword)
                .distinct();
        return UserConvert.INSTANCE.convert(this.selectList(wrapper));
    }
}
