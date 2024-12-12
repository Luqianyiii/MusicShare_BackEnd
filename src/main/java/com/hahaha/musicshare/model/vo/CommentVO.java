package com.hahaha.musicshare.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "评论信息")
public class CommentVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -45095106764580159L;

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "点赞数")
    private Integer likes_count;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "评论者ID")
    private Integer commenter_id;

    @Schema(description = "评论者昵称")
    private String commenter_nickname;

    @Schema(description = "评论对象类型")
    private String comment_object_type;

    @Schema(description = "评论对象ID")
    private Integer comment_object_id;
}
