package com.hahaha.musicshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.musicshare.model.entity.Music;
import com.hahaha.musicshare.model.vo.MusicVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MusicService extends IService<Music> {

    //获取热门音乐
    List<MusicVO> getHostMusic(Integer num);

    //获取最新音乐
    List<MusicVO> getLatestMusic();

    //根据作者id获取音乐
    List<MusicVO> getMusicByAuthorId();

    //用户根据关键词搜索音乐
    List<MusicVO> getMusicByKeyword(String keyword);

    //管理员根据关键词搜索音乐
    List<MusicVO> getMusicByKeyword(String keyword,int a);

    //管理员获取需要审核的音乐
    List<MusicVO> getMusicNeedAudit();

    //上传音乐信息到云端
    void shareMusic(MultipartFile cover, MultipartFile music, String song_name, String description, String keywords);

    //更新音乐状态
    void updateMusicStatus(Integer music_id, String status);

    void clickMusic(Integer music_id);
}
