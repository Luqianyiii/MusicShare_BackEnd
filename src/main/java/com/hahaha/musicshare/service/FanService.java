package com.hahaha.musicshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.musicshare.model.dto.FanDTO;
import com.hahaha.musicshare.model.entity.Fan;
import com.hahaha.musicshare.model.vo.FanVO;

import java.util.List;

public interface FanService extends IService<Fan> {

    //添加粉丝
    void addFan(FanDTO fanDTO);

    //删除粉丝
    void deleteFan(Integer fans_id);

    //获取粉丝列表
    List<FanVO> getFans();
    
    //获取关注列表
    List<FanVO> getFollowed();

    Long getFansCount();

    Long getFollowedCount();


}
