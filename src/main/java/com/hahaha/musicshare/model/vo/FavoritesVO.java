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
}
