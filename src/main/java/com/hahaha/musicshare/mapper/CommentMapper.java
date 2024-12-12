package com.hahaha.musicshare.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.musicshare.model.entity.Comment;

public interface CommentMapper extends BaseMapper<Comment> {
    default Comment getById(Integer id) {
        return this.selectOne(new LambdaQueryWrapper<Comment>().eq(Comment::getId, id));
    }
//    TODO 复杂条件查询评论
}
