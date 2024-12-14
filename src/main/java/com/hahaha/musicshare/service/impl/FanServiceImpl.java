package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.mapper.FanMapper;
import com.hahaha.musicshare.model.entity.Fan;
import com.hahaha.musicshare.model.vo.FanVO;
import com.hahaha.musicshare.service.FanService;

import java.util.List;

public class FanServiceImpl extends ServiceImpl<FanMapper, Fan> implements FanService {
    @Override
    public void addFan(Integer fa_id, Integer followed_id) {

    }

    @Override
    public void deleteFan(Integer fans_id) {

    }

    @Override
    public List<FanVO> getFans(Integer user_id) {
        return List.of();
    }

    @Override
    public List<FanVO> getFollowed(Integer user_id) {
        return List.of();
    }
}

