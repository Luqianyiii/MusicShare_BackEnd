package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.common.exception.ErrorCode;
import com.hahaha.musicshare.common.exception.ServerException;
import com.hahaha.musicshare.mapper.MusicMapper;
import com.hahaha.musicshare.model.dto.MusicDTO;
import com.hahaha.musicshare.model.entity.Music;
import com.hahaha.musicshare.model.vo.MusicVO;
import com.hahaha.musicshare.service.MusicService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {
    @Override
    public List<MusicVO> getHostMusic(Integer num) {
        return baseMapper.Host(num);
    }

    @Override
    public List<MusicVO> getLatestMusic() {
        return baseMapper.Latest();
    }

    @Override
    public List<MusicVO> getMusicByAuthorId() {
        return baseMapper.myWorks(RequestContext.getUserId());
    }

    @Override
    public List<MusicVO> getMusicByKeyword(String keyword) {
        return baseMapper.searchMusicFromUser(keyword);
    }

    @Override
    public List<MusicVO> getMusicByKeyword(String keyword, int a) {
        return baseMapper.searchMusicFromAdmin(keyword);
    }

    @Override
    public List<MusicVO> getMusicNeedAudit() {
        return baseMapper.MusicForAdmin();
    }

    @Override
    public void shareMusic(MusicDTO musicDTO) {
//        TODO
    }

    @Override
    public void updateMusicStatus(Integer music_id, String status) {
        Music music = new Music();
        music.setStatus(status);
        if (baseMapper.updateById(music) < 1) {
            throw new ServerException(ErrorCode.OPERATION_FAIL);
        }
    }
}
