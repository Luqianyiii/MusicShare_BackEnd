package com.hahaha.musicshare.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "⽤户修改dto")
public class UserEditDTO {
    @Schema(description = "主键")
    private Integer Id;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "个性签名")
    private String motto;

    @Schema(description = "年龄")
    private Integer age;
}
