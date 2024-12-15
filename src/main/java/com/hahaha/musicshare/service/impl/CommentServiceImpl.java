package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.convert.CommentConvert;
import com.hahaha.musicshare.mapper.CommentMapper;
import com.hahaha.musicshare.model.dto.CommentDTO;
import com.hahaha.musicshare.model.entity.Comment;
import com.hahaha.musicshare.model.vo.CommentVO;
import com.hahaha.musicshare.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Override
    public CommentVO addComment(CommentDTO commentDTO) {
        Comment comment = CommentConvert.INSTANCE.convert(commentDTO);
        CommentVO commentVO = CommentConvert.INSTANCE.convertTOVO(commentDTO);
        commentVO.setId(baseMapper.insert(comment));
        return commentVO;
    }

    @Override
    public void deleteComment(Integer comment_id) {
        baseMapper.deleteById(comment_id);
    }

    @Override
    public List<CommentVO> getCommentByUserId(Integer user_id) {
        return baseMapper.getByUserId(user_id);
    }

    @Override
    public List<CommentVO> getCommentByMusicId(Integer music_id) {
        return baseMapper.getByMusicId(music_id);
    }

    @Override
    public void likes(Integer comment_id) {
        Comment comment = baseMapper.selectById(comment_id);
        comment.setLikes_count(comment.getLikes_count() + 1);
        baseMapper.updateById(comment);
    }

    @Override
    public void unlikes(Integer comment_id) {
        Comment comment = baseMapper.selectById(comment_id);
        comment.setLikes_count(comment.getLikes_count() - 1);
        baseMapper.updateById(comment);
    }
}
                                                                                