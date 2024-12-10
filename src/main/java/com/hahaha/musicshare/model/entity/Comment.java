package com.hahaha.musicshare.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comments")
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer likes_count;
    private String content;
    private Integer commenter_id;
    private String comment_object_type;
    private Integer comment_object_id;
}
