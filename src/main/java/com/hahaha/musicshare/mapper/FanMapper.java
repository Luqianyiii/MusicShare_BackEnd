package com.hahaha.musicshare.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hahaha.musicshare.model.entity.Fan;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.FanVO;

import java.util.List;
public interface FanMapper extends MPJBaseMapper<Fan> {

    // 查关注的人
    default List<FanVO> getFollowed(Integer fanId) {
        MPJLambdaWrapper<Fan> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Fan::getId, Fan::getFan_id, Fan::getFollowed_id)  // 精确选择字段
                .select(User::getAvatar, User::getNickname, User::getGender, User::getMotto)
                .leftJoin(User.class, User::getId, Fan::getFollowed_id)  // 定义联表条件
                .eq(Fan::getFan_id, fanId);  // 添加条件
        return this.selectJoinList(FanVO.class, wrapper);
    }

    // 查粉丝
    default List<FanVO> getFans(Integer followedId) {
        MPJLambdaWrapper<Fan> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Fan::getId, Fan::getFan_id, Fan::getFollowed_id)
                .select(User::getAvatar, User::getNickname, User::getGender, User::getMotto)
                .leftJoin(User.class, User::getId, Fan::getFan_id)
                .eq(Fan::getFollowed_id, followedId);
        System.out.println(wrapper.getSqlSegment());
        return this.selectJoinList(FanVO.class, wrapper);
    }

    // 粉丝数量
    default long getFansCount(Integer followedId) {
        MPJLambdaWrapper<Fan> wrapper = new MPJLambdaWrapper<>();
        wrapper.eq(Fan::getFollowed_id, followedId);
        return this.selectCount(wrapper);
    }

    // 关注数量
    default long getFollowedCount(Integer fanId) {
        MPJLambdaWrapper<Fan> wrapper = new MPJLambdaWrapper<>();
        wrapper.eq(Fan::getFan_id, fanId);
        return this.selectCount(wrapper);
    }
}
