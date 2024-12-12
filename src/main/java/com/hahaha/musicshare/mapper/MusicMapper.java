package com.hahaha.musicshare.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.musicshare.model.entity.Music;

public interface MusicMapper extends BaseMapper<Music> {
    default Music getById(Integer id) {
        return this.selectOne(new LambdaQueryWrapper<Music>().eq(Music::getId, id));
    }
}
