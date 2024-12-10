package com.hahaha.musicshare.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "⽤户登录vo")
public class UserLoginVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8212240698099812005L;

    @Schema(description = "⽤户ID")
    private Integer Id;

    @Schema(description = "⼿机号")
    private String phone;

    @Schema(description = "令牌")
    private String accessToken;

    @Schema(description = "身份标识")
    private String remark;
}