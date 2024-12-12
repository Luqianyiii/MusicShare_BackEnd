package com.hahaha.musicshare.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hahaha.musicshare.model.entity.Favorites;

public interface FavoritesMapper extends BaseMapper<Favorites> {
    default Favorites getById(Integer id) {
        return this.selectOne(new LambdaQueryWrapper<Favorites>().eq(Favorites::getId, id));
    }
}
