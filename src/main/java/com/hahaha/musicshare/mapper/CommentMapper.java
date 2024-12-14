package com.hahaha.musicshare.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.musicshare.model.entity.Comment;
import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {
    default Comment getById(Integer id) {
        return this.selectOne(new LambdaQueryWrapper<Comment>().eq(Comment::getId, id));
    }

    default List<Comment> getBySongId(Integer songId) {
        return this.selectList(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getComment_object_id, songId)
                .eq(Comment::getComment_object_type, "作品"));
    }

    default List<Comment> getByUserId(Integer UserId) {
        return this.selectList(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getComment_object_id, UserId)
                .eq(Comment::getComment_object_type, "评论"));
    }

}
