package com.hahaha.musicshare.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "收藏信息")
public class FavoritesVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -45095106764580159L;

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "音乐ID")
    private Integer music_id;

    @Schema(description = "收藏者ID")
    private Integer lover_id;

    @Schema(description = "歌曲名称")
    private String song_name;

    @Schema(description = "作者ID")
    private Integer author_id;

    @Schema(description = "作着昵称")
    private String author_name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "链接")
    private String link;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "关键词")
    private String keywords;

    @Schema(description = "点击数")
    private Integer clicks;
}