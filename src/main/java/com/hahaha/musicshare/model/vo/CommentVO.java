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
    private String comment_nickname;

    @Schema(description = "评论者头像")
    private String comment_avatar;

    @Schema(description = "评论作品ID")
    private Integer comment_music_id;

    @Schema(description = "评论接收对象")
    private Integer recipient_id;
}
