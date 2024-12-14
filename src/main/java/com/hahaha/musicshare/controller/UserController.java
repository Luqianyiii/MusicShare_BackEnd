package com.hahaha.musicshare.controller;

import com.hahaha.musicshare.common.result.Result;
import com.hahaha.musicshare.model.dto.UserEditDTO;
import com.hahaha.musicshare.model.vo.UserInfoVO;
import com.hahaha.musicshare.service.CommunicationService;
import com.hahaha.musicshare.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "⽤户接⼝")
public class UserController {
    private final UserService userService;
    private final CommunicationService communicationService;

    @PostMapping("/info")
    @Operation(summary = "获取⽤户信息")
    public Result<UserInfoVO> getInfo(){
        return Result.ok(userService.userInfo());
    }

    @PostMapping("/update")
    @Operation(summary = "修改⽤户信息")
    public Result<UserInfoVO> update(@RequestBody UserEditDTO userEditDTO){
        return Result.ok(userService.updateInfo(userEditDTO));
    }

    @PostMapping(value = "/upload/avatar")
    @Operation(summary = "头像上传")
    public Result<String> upload(@RequestBody MultipartFile file) {
        return Result.ok(communicationService.uploadAvatar(file));
    }

    @PostMapping(value = "/changePassword")
    @Operation(summary = "更改密码")
    public Result<String> changePassword(@RequestParam("phone") String phone,
                                         @RequestParam("code") String code,
                                         @RequestParam("password") String password,
                                         @RequestHeader("Authorization") String accessToken) {
        communicationService.updatePassword(phone, code, password, accessToken);
        return Result.ok();
    }
}