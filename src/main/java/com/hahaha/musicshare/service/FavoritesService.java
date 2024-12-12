package com.hahaha.musicshare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hahaha.musicshare.model.entity.Favorites;
import com.hahaha.musicshare.model.vo.FavoritesVO;

import java.util.List;

public interface FavoritesService extends IService<Favorites> {

    //添加收藏
    void addFavorites(Integer user_id, Integer music_id);

    //删除收藏
    void deleteFavorites(Integer user_id, Integer music_id);

    //获取收藏列表
    List<FavoritesVO> getFavorites(Integer user_id);
}
