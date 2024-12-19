package com.hahaha.musicshare.controller;

import com.hahaha.musicshare.common.result.Result;
import com.hahaha.musicshare.model.vo.MusicVO;
import com.hahaha.musicshare.model.vo.UserInfoVO;
import com.hahaha.musicshare.service.ManagerService;
import com.hahaha.musicshare.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/manager")
@AllArgsConstructor
@Tag(name = "管理层")
public class ManagerController {
    private final MusicService musicService;
    private final ManagerService managerService;

    @PostMapping("/Users")
    @Operation(summary = "获取所有用户信息")
    public Result<List<UserInfoVO>> userInfo() {
        return Result.ok(managerService.getAllUser());
    }

    @PostMapping("/SearchUsers")
    @Operation(summary = "模糊搜索用户")
    public Result<List<UserInfoVO>> searchUser(@RequestParam("keyword") String keyword) {
        return Result.ok(managerService.getUserByKeyword(keyword));
    }

    @PostMapping(value = "/DeleteUser")
    @Operation(summary = "删除用户")
    public Result<String> deleteUser(@RequestParam("user_id") Integer user_id) {
        managerService.deleteUser(user_id);
        return Result.ok();
    }

    @PostMapping(value = "/SearchMusic")
    @Operation(summary = "管理员模糊搜索")
    public Result<List<MusicVO>> getLatestMusic(@RequestParam("keyword") String keyword) {
        return Result.ok(musicService.getMusicByKeyword(keyword, 0));
    }

    @PostMapping(value = "/GetMusicNeedAudit")
    @Operation(summary = "获取待审核")
    public Result<List<MusicVO>> getMusicNeedAudit() {
        return Result.ok(musicService.getMusicNeedAudit());
    }

    @PostMapping(value = "/UpdateMusic")
    @Operation(summary = "更新作品状态")
    public Result<String> updateMusic(@RequestParam("music_id") Integer music_id, @RequestParam("status") String status) {
        musicService.updateMusicStatus(music_id, status);
        return Result.ok();
    }

    @PostMapping(value = "/DeleteMusic")
    @Operation(summary = "删除音乐")
    public Result<String> deleteMusic(@RequestParam("id") Integer id) {
        musicService.deleteMusic(id);
        return Result.ok();
    }
}
