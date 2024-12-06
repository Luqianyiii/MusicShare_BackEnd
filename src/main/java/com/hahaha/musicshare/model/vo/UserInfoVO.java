package com.hahaha.musicshare.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "⽤户信息")
public class UserInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -45095106764580159L;
    @Schema(description = "主键")
    private Integer Id;

    @Schema(description = "⼿机号")
    private String phone;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "个性签名")
    private String motto;

    @Schema(description = "⽤户类型")
    private String remark;

    @Schema(description = "年龄")
    private Integer age;


}