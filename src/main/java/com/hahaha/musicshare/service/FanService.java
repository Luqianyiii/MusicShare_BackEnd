package com.hahaha.musicshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.musicshare.model.entity.Fan;
import com.hahaha.musicshare.model.vo.FanVO;

import java.util.List;

public interface FanService extends IService<Fan> {

    //添加粉丝
    void addFan(Integer fa_id, Integer followed_id);

    //删除粉丝
    void deleteFan(Integer fans_id);

    //获取粉丝
    List<FanVO> getFans(Integer user_id);


}