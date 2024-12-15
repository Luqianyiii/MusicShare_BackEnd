package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.convert.FanConvert;
import com.hahaha.musicshare.mapper.FanMapper;
import com.hahaha.musicshare.model.dto.FanDTO;
import com.hahaha.musicshare.model.entity.Fan;
import com.hahaha.musicshare.model.vo.FanVO;
import com.hahaha.musicshare.service.FanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FanServiceImpl extends ServiceImpl<FanMapper, Fan> implements FanService {
    @Override
    public void addFan(FanDTO fanDTO) {
        Fan fan = FanConvert.INSTANCE.convert(fanDTO);
//        TODO 向被关注者发送一条通知
        baseMapper.insert(fan);
    }

    @Override
    public void deleteFan(Integer fans_id) {
        baseMapper.deleteById(fans_id);
    }

    @Override
    public List<FanVO> getFans() {
        return baseMapper.getFan(RequestContext.getUserId());
    }

    @Override
    public List<FanVO> getFollowed() {
        return baseMapper.getFollowed(RequestContext.getUserId());
    }
}

