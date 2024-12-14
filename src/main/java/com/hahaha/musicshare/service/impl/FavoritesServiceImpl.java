package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.mapper.FavoritesMapper;
import com.hahaha.musicshare.model.entity.Favorites;
import com.hahaha.musicshare.model.vo.FavoritesVO;
import com.hahaha.musicshare.service.FavoritesService;

import java.util.List;

public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements FavoritesService {
    @Override
    public void addFavorites(Integer user_id, Integer music_id) {

    }

    @Override
    public void deleteFavorites(Integer id) {

    }

    @Override
    public List<FavoritesVO> getFavorites(Integer user_id) {
        return List.of();
    }
}
