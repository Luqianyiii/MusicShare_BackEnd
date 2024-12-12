package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.mapper.CommentMapper;
import com.hahaha.musicshare.model.dto.CommentDTO;
import com.hahaha.musicshare.model.entity.Comment;
import com.hahaha.musicshare.model.vo.CommentVO;
import com.hahaha.musicshare.service.CommentService;

import java.util.List;

public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Override
    public void addComment(CommentDTO comment) {

    }

    @Override
    public void deleteComment(Integer comment_id) {

    }

    @Override
    public List<CommentVO> getCommentByUserId(Integer user_id) {
        return List.of();
    }

    @Override
    public List<CommentVO> getCommentByMusicId(Integer music_id) {
        return List.of();
    }

    @Override
    public void likes(Integer comment_id) {

    }

    @Override
    public void unlikes(Integer comment_id) {

    }
}
                                                                                