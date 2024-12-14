package com.hahaha.musicshare.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class FanDTO {

    @Schema(description = "粉丝ID")
    private Integer fan_id;

    @Schema(description = "被关注者ID")
    private Integer followed_id;
}
