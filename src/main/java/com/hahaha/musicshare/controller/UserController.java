package com.hahaha.musicshare.controller;

import com.hahaha.musicshare.common.result.Result;
import com.hahaha.musicshare.model.dto.FanDTO;
import com.hahaha.musicshare.model.dto.UserEditDTO;
import com.hahaha.musicshare.model.entity.Notification;
import com.hahaha.musicshare.model.vo.CommentVO;
import com.hahaha.musicshare.model.vo.FanVO;
import com.hahaha.musicshare.model.vo.UserInfoVO;
import com.hahaha.musicshare.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "⽤户接⼝")
public class UserController {
    private final UserService userService;
    private final CommunicationService communicationService;
    private final CommentService commentService;
    private final FanService fanService;
    private final NotificationService notificationService;

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
    public Result<String> changePassword(@RequestParam("phone") String phone,@RequestParam("code") String code,
                          @RequestParam("password") String password,@RequestHeader("Authorization") String accessToken) {
        return Result.ok(communicationService.updatePassword(phone, code, password, accessToken));
    }

    @PostMapping(value = "/Comment")
    @Operation(summary = "获取通知")
    public Result<List<CommentVO>> getComment() {
        return Result.ok(commentService.getCommentByUserId());
    }

    @PostMapping(value = "/Fan")
    @Operation(summary = "获取粉丝")
    public Result<List<FanVO>> getFan() {
        return Result.ok(fanService.getFans());
    }

    @PostMapping(value = "/Followed")
    @Operation(summary = "获取关注")
    public Result<List<FanVO>> getFollowed() {
        return Result.ok(fanService.getFollowed());
    }

    @PostMapping(value = "/AddFan")
    @Operation(summary = "添加粉丝信息")
    public Result<String> AddFan(@RequestBody FanDTO fanDTO) {
        fanService.addFan(fanDTO);
        notificationService.addNotification(fanDTO.getFollowed_id(),"您有新的粉丝!");
        return Result.ok();
    }

    @PostMapping(value = "/DeleteFan")
    @Operation(summary = "删除粉丝信息")
    public Result<String> DeleteFan(@RequestParam("id") Integer id) {
        fanService.deleteFan(id);
        return Result.ok();
    }

    @PostMapping(value = "/CountFan")
    @Operation(summary = "获取粉丝数")
    public Result<String> CountFan() {
        return Result.ok(fanService.getFansCount().toString());
    }

    @PostMapping(value = "/CountFollowed")
    @Operation(summary = "获取关注数")
    public Result<String> CountFollowed() {
        return Result.ok(fanService.getFollowedCount().toString());
    }

    @PostMapping(value = "/GetNotification")
    @Operation(summary = "获取通知")
    public Result<List<Notification>> getNotification() {
        return Result.ok(notificationService.getNotificationByUserId());
    }

 @PostMapping(value = "/DeleteNotification")
    @Operation(summary = "删除通知")
    public Result<String> DeleteNotification(@RequestParam("id") Integer id) {
     notificationService.deleteNotificationById(id);
        return Result.ok();
 }
}