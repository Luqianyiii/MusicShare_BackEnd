package com.hahaha.musicshare.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("fans")
public class Fan {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer fan_id;
    private Integer followed_id;
}
