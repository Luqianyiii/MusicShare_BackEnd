package com.hahaha.musicshare.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "修改个人信息")
public class UserEditDTO {

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "个性签名")
    private String motto;

    @Schema(description = "年龄")
    private Integer age;
}
