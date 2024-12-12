package com.hahaha.musicshare.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "分享音乐信息")
public class MusicDTO {

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
}
