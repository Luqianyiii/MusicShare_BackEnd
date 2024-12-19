package com.hahaha.musicshare.controller;

import com.hahaha.musicshare.common.result.Result;
import com.hahaha.musicshare.model.dto.CommentDTO;
import com.hahaha.musicshare.model.dto.FanDTO;
import com.hahaha.musicshare.model.dto.UserEditDTO;
import com.hahaha.musicshare.model.entity.Notification;
import com.hahaha.musicshare.model.vo.*;
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
    private final FavoritesService favoritesService;
    private final MusicService musicService;

    @PostMapping("/info")
    @Operation(summary = "获取⽤户信息")
    public Result<UserInfoVO> getInfo() {
        return Result.ok(userService.userInfo());
    }

    @PostMapping("/update")
    @Operation(summary = "修改⽤户信息")
    public Result<UserInfoVO> update(@RequestBody UserEditDTO userEditDTO) {
        return Result.ok(userService.updateInfo(userEditDTO));
    }

    @PostMapping(value = "/upload/avatar")
    @Operation(summary = "头像上传")
    public Result<String> upload(@RequestBody MultipartFile file) {
        return Result.ok(communicationService.uploadAvatar(file));
    }

    @PostMapping(value = "/changePassword")
    @Operation(summary = "更改密码")
    public Result<String> changePassword(@RequestParam("phone") String phone, @RequestParam("code") String code,
                                         @RequestParam("password") String password, @RequestHeader("Authorization") String accessToken) {
        return Result.ok(communicationService.updatePassword(phone, code, password, accessToken));
    }

    @PostMapping(value = "/Comment")
    @Operation(summary = "获取评论")
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
    public Result<String> addFan(@RequestBody FanDTO fanDTO) {
        fanService.addFan(fanDTO);
        notificationService.addNotification(fanDTO.getFollowed_id(), "您有新的粉丝!");
        return Result.ok();
    }

    @PostMapping(value = "/DeleteFan")
    @Operation(summary = "删除粉丝信息")
    public Result<String> deleteFan(@RequestParam("id") Integer id) {
        fanService.deleteFan(id);
        return Result.ok();
    }

    @PostMapping(value = "/CountFan")
    @Operation(summary = "获取粉丝数")
    public Result<String> countFan() {
        return Result.ok(fanService.getFansCount().toString());
    }

    @PostMapping(value = "/CountFollowed")
    @Operation(summary = "获取关注数")
    public Result<String> countFollowed() {
        return Result.ok(fanService.getFollowedCount().toString());
    }

    @PostMapping(value = "/GetNotification")
    @Operation(summary = "获取通知")
    public Result<List<Notification>> getNotification() {
        return Result.ok(notificationService.getNotificationByUserId());
    }

    @PostMapping(value = "/DeleteNotification")
    @Operation(summary = "删除通知")
    public Result<String> deleteNotification(@RequestParam("id") Integer id) {
        notificationService.deleteNotificationById(id);
        return Result.ok();
    }

    @PostMapping(value = "/Favorites")
    @Operation(summary = "获取收藏")
    public Result<List<FavoritesVO>> getFavorites() {
        return Result.ok(favoritesService.getFavorites());
    }

    @PostMapping(value = "/AddFavorites")
    @Operation(summary = "添加收藏")
    public Result<String> addFavorites(@RequestParam("music_id") Integer music_id) {
        favoritesService.addFavorites(music_id);
        return Result.ok();
    }

    @PostMapping(value = "/DeleteFavorites")
    @Operation(summary = "删除收藏")
    public Result<String> deleteFavorites(@RequestParam("id") Integer id) {
        favoritesService.deleteFavorites(id);
        return Result.ok();
    }

    @PostMapping(value = "/GetHostMusic")
    @Operation(summary = "获取热门音乐")
    public Result<List<MusicVO>> getHostMusic(@RequestParam("number") Integer num) {
        return Result.ok(musicService.getHostMusic(num));
    }

    @PostMapping(value = "/GetLatestMusic")
    @Operation(summary = "获取最新作品")
    public Result<List<MusicVO>> getLatestMusic() {
        return Result.ok(musicService.getLatestMusic());
    }

    @PostMapping(value = "/GetMyWorks")
    @Operation(summary = "获取个人作品")
    public Result<List<MusicVO>> getMyWorks() {
        return Result.ok(musicService.getMusicByAuthorId());
    }

    @PostMapping(value = "/SearchMusic")
    @Operation(summary = "用户模糊搜索")
    public Result<List<MusicVO>> SearchMusic(@RequestParam("keyword") String keyword) {
        return Result.ok(musicService.getMusicByKeyword(keyword));
    }

    @PostMapping(value = "/UpdateMusic")
    @Operation(summary = "更新作品状态")
    public Result<String> updateMusic(@RequestParam("music_id") Integer music_id, @RequestParam("status") String status) {
        musicService.updateMusicStatus(music_id, status);
        return Result.ok();
    }

    @PostMapping(value = "/UploadMusic")
    @Operation(summary = "上传作品")
    public Result<String> uploadMusic(@RequestBody MultipartFile cover, @RequestBody MultipartFile music,
                                      @RequestParam("name") String name, @RequestParam("description") String description,
                                      @RequestParam("keywords") String keywords) {
        musicService.shareMusic(cover, music, name, description, keywords);
        return Result.ok();
    }

    @PostMapping(value = "/Click")
    @Operation(summary = "作品被点击")
    public Result<String> clickMusic(@RequestParam("music_id") Integer music_id) {
        musicService.clickMusic(music_id);
        return Result.ok();
    }

    @PostMapping(value = "/AddComment")
    @Operation(summary = "添加评论")
    public Result<String> addComment(@RequestBody CommentDTO commentDTO) {
        commentService.addComment(commentDTO);
        return Result.ok();
    }

    @PostMapping(value = "/DeleteComment")
    @Operation(summary = "删除评论")
    public Result<String> deleteComment(@RequestParam("id") Integer id) {
        commentService.deleteComment(id);
        return Result.ok();
    }

    @PostMapping(value = "/MusicComment")
    @Operation(summary = "获取作品评论")
    public Result<List<CommentVO>> SearchMusic(@RequestParam("id") Integer id) {
        return Result.ok(commentService.getCommentByMusicId(id));
    }

    @PostMapping(value = "/LikesComment")
    @Operation(summary = "点赞评论")
    public Result<String> likesComment(@RequestParam("id") Integer id) {
        commentService.likes(id);
        return Result.ok();
    }

    @PostMapping(value = "/DislikesComment")
    @Operation(summary = "取消点赞评论")
    public Result<String> dislikesComment(@RequestParam("id") Integer id) {
        commentService.dislikes(id);
        return Result.ok();
    }

    @PostMapping(value = "/IsFavorite")
    @Operation(summary = "判断是否收藏")
    public Result<Boolean> isFavorite(@RequestParam("id") Integer id) {
        return Result.ok(favoritesService.isFavorite(id));
    }

    @PostMapping(value = "/GetFollowedWorks")
    @Operation(summary = "获取关注作品")
    public Result<List<MusicVO>> getFollowedWorks(@RequestParam("id") Integer id) {
        return Result.ok(musicService.getMusicByAuthorId(id));
    }

    @PostMapping(value = "/GetAllFollowedWorks")
    @Operation(summary = "获取所有关注的人的作品")
    public Result<List<MusicVO>> getAllFollowedWorks() {
        return Result.ok(musicService.getAllFollowedMusic());
    }
}