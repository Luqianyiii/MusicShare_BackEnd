package com.hahaha.musicshare.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "新增评论信息")
public class CommentDTO {

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "评论者ID")
    private Integer commenter_id;

    @Schema(description = "评论对象类型")
    private String comment_object_type;

    @Schema(description = "评论对象ID")
    private Integer comment_object_id;
}
