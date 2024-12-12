package com.hahaha.musicshare.convert;

import com.hahaha.musicshare.model.entity.Favorites;
import com.hahaha.musicshare.model.vo.FavoritesVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FavoritesConvert {

    //获取 FavoritesConvert 实例
    FavoritesConvert INSTANCE = Mappers.getMapper(FavoritesConvert.class);

    //将 Favorites 对象转换为 FavoritesVO 对象
    FavoritesVO convert(Favorites favorites);
}

