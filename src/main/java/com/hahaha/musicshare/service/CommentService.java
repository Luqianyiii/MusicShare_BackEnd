package com.hahaha.musicshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.musicshare.model.dto.CommentDTO;
import com.hahaha.musicshare.model.entity.Comment;
import com.hahaha.musicshare.model.vo.CommentVO;

import java.util.List;

public interface CommentService extends IService<Comment> {

    //添加评论
    CommentVO addComment(CommentDTO comment);

    //删除评论
    void deleteComment(Integer comment_id);

    //根据用户id查询评论
    List<CommentVO> getCommentByUserId();

    //根据歌曲id查询评论
    List<CommentVO> getCommentByMusicId(Integer music_id);

    //点赞评论
    void likes(Integer comment_id);

    //取消点赞评论
    void unlikes(Integer comment_id);

}
