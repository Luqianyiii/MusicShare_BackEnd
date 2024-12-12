package com.hahaha.musicshare.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "通知信息")
public class NotificationVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -45095106764580159L;

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "接收者ID")
    private Integer recipient_id;

    @Schema(description = "通知内容")
    private String content;
}
