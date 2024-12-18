package com.hahaha.musicshare.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hahaha.musicshare.model.entity.Favorites;
import com.hahaha.musicshare.model.entity.Music;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.FavoritesVO;

import java.util.List;
public interface FavoritesMapper extends MPJBaseMapper<Favorites> {
    default List<FavoritesVO> getByLoverId(Integer lover_id) {
        MPJLambdaWrapper<Favorites> wrapper = new MPJLambdaWrapper<>(Favorites.class);
        wrapper.eq(Favorites::getLover_id, lover_id)
               .leftJoin(Music.class,Music::getId, Favorites::getMusic_id)
               .select(Music::getSong_name,Music::getDescription,Music::getCover,
                       Music::getKeywords,Music::getClicks,Music::getLink)
               .leftJoin(User.class,User::getId,Music::getAuthor_id)
                .selectAs(User::getNickname,"author_name");
        return this.selectJoinList(FavoritesVO.class, wrapper);

    }
    default boolean isFavorite(Integer music_id,Integer lover_id) {
        return this.selectCount(new MPJLambdaWrapper<Favorites>(Favorites.class)
                .eq(Favorites::getMusic_id,music_id)
                .eq(Favorites::getLover_id,lover_id)) > 0;
    }
}
