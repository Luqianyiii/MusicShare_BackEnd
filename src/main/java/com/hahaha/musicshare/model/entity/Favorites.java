package com.hahaha.musicshare.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("musiclove")
public class Favorites {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer music_id;
    private Integer lover_id;
}
