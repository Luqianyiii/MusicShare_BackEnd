package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.mapper.MusicMapper;
import com.hahaha.musicshare.model.dto.MusicDTO;
import com.hahaha.musicshare.model.entity.Music;
import com.hahaha.musicshare.model.vo.MusicVO;
import com.hahaha.musicshare.service.MusicService;

import java.util.List;

public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {
    @Override
    public List<MusicVO> getBestMusic() {
        return List.of();
    }

    @Override
    public List<MusicVO> getLatestMusic() {
        return List.of();
    }

    @Override
    public List<MusicVO> getMusicByUserId(Integer user_id) {
        return List.of();
    }

    @Override
    public List<MusicVO> getMusicByKeyword(String keyword) {
        return List.of();
    }

    @Override
    public List<MusicVO> getMusicByKeyword(String keyword, int a) {
        return List.of();
    }

    @Override
    public void shareMusic(MusicDTO musicDTO) {

    }

    @Override
    public void updateMusicStatus(Integer music_id, Integer status) {

    }
}
