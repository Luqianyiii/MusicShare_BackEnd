package com.hahaha.musicshare.mapper;


import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hahaha.musicshare.model.entity.Comment;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.CommentVO;

import java.util.List;

public interface CommentMapper extends MPJBaseMapper<Comment> {

//    根据被评论作用id查询评论
    default List<CommentVO> getByMusicId(Integer songId) {
        MPJLambdaWrapper<Comment> wrapper = AfterJoin().eq(Comment::getComment_music_id, songId);
        return this.selectJoinList(CommentVO.class, wrapper);
    }

//    根据接收评论用户id查询评论
    default List<CommentVO> getByUserId(Integer UserId) {
        MPJLambdaWrapper<Comment> wrapper = AfterJoin().eq(Comment::getRecipient_id, UserId);
        return this.selectJoinList(CommentVO.class, wrapper);
    }

//    联查评论者信息
    private static MPJLambdaWrapper<Comment> AfterJoin() {
        MPJLambdaWrapper<Comment> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(Comment.class)
                .leftJoin(User.class, User::getId, Comment::getCommenter_id)
                .select(User::getNickname,User::getAvatar);
        return wrapper;
    }


}
