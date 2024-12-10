package com.hahaha.musicshare.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("musiclibrary")
public class Music {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String song_name;
    private Integer author_id;
    private String description;
    private String link;
    private String cover;
    private String status;
    private String keywords;
    private Integer clicks;
}
