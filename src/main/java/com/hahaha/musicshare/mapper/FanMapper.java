package com.hahaha.musicshare.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hahaha.musicshare.model.entity.Fan;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.FanVO;

import java.util.List;

public interface FanMapper extends MPJBaseMapper<Fan> {
    MPJLambdaWrapper<Fan> wrapper = new MPJLambdaWrapper<>();

//    查关注的人
    default List<FanVO> getFollowed(Integer fanId) {
        wrapper.selectAll(Fan.class)
                .leftJoin(User.class, User::getId,Fan::getFan_id)
                .select(User::getAvatar,User::getNickname,User::getGender,User::getMotto)
                .eq(Fan::getFan_id, fanId);
    return this.selectJoinList(FanVO.class, wrapper);
    }

//    查粉丝
    default List<FanVO> getFan(Integer followedId) {
        wrapper.selectAll(Fan.class)
                .leftJoin(User.class, User::getId,Fan::getFollowed_id)
                .select(User::getAvatar,User::getNickname,User::getGender,User::getMotto)
                .eq(Fan::getFollowed_id, followedId);
        return this.selectJoinList(FanVO.class, wrapper);
        }
}
