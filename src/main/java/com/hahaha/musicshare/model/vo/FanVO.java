package com.hahaha.musicshare.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "粉丝信息")
public class FanVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -45095106764580159L;

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "粉丝ID")
    private Integer fan_id;

    @Schema(description = "被关注者ID")
    private Integer followed_id;
}
