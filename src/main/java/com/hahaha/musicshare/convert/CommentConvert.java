package com.hahaha.musicshare.convert;

import com.hahaha.musicshare.model.dto.CommentDTO;
import com.hahaha.musicshare.model.entity.Comment;
import com.hahaha.musicshare.model.vo.CommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentConvert {
    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

    /**
     * @Mapping (source = "id", target = "commentId")
     * 需要时可以使用此注解为方法规定转换参照
     */

    //将 CommentDTO 对象转换为 Comment 对象
    Comment convert(CommentDTO dto);

    //将 Comment 对象转换为 CommentVO 对象
    CommentVO convert(Comment comment);
}