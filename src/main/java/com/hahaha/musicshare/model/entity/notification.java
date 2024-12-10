package com.hahaha.musicshare.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("notifications")
public class notification {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer recipient_id;
    private String content;
}
