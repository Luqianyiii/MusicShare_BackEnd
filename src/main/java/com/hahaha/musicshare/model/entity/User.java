package com.hahaha.musicshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("personal_information")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String phone;
    private String motto;
    private String nickname;
    private String gender;
    private Integer age;
    private String remark;
    private String password;
    /**
     * @see com.hahaha.musicshare.enums.AccountStatusEnum
     */

}
