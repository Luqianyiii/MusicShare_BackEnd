package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.mapper.FavoritesMapper;
import com.hahaha.musicshare.model.entity.Favorites;
import com.hahaha.musicshare.model.vo.FavoritesVO;
import com.hahaha.musicshare.service.FavoritesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements FavoritesService {
    @Override
    public void addFavorites(Integer music_id) {
        Favorites favorites = new Favorites();
        favorites.setLover_id(RequestContext.getUserId());
        favorites.setMusic_id(music_id);
        baseMapper.insert(favorites);
    }

    @Override
    public void deleteFavorites(Integer id) {
        baseMapper.deleteById(id);
    }

    @Override
    public List<FavoritesVO> getFavorites() {
        return baseMapper.getByLoverId(RequestContext.getUserId());
    }

    @Override
    public boolean isFavorite(Integer music_id) {
        return baseMapper.isFavorite(music_id, RequestContext.getUserId());
    }
}
