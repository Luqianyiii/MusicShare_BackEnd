package com.hahaha.musicshare.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hahaha.musicshare.model.entity.Music;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.MusicVO;
import org.mapstruct.Mapper;

import java.util.List;

public interface MusicMapper extends MPJBaseMapper<Music> {

    //模糊搜索,多表联查
    private static MPJLambdaWrapper<Music> SearchMusic(String keyword, MPJLambdaWrapper<Music> wrapper) {
        return wrapper.selectAll()
                .leftJoin(User.class, User::getId, Music::getAuthor_id)
                .selectAs(User::getNickname, "author_name")
                .distinct()
                .like(Music::getKeywords, keyword)
                .or()
                .like(Music::getSong_name, keyword)
                .or()
                .like(User::getNickname, keyword);
    }

    //用户模糊搜索
    default List<MusicVO> searchMusicFromUser(String keyword) {
        MPJLambdaWrapper<Music> wrapper = new MPJLambdaWrapper<>();
        SearchMusic(keyword, wrapper).eq(Music::getStatus, "上线中");
        return this.selectJoinList(MusicVO.class, wrapper);
    }

    //管理员模糊搜索
    default List<MusicVO> searchMusicFromAdmin(String keyword) {
        MPJLambdaWrapper<Music> wrapper = new MPJLambdaWrapper<>();
        SearchMusic(keyword, wrapper);
        return this.selectJoinList(MusicVO.class, wrapper);
    }

    //需审核
    default List<MusicVO> MusicForAdmin() {
        MPJLambdaWrapper<Music> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll()
                .eq(Music::getStatus, "待审核")
                .leftJoin(User.class, User::getId, Music::getAuthor_id)
                .selectAs(User::getNickname, "author_name");
        return this.selectJoinList(MusicVO.class, wrapper);
    }

    //限制数量,按点击量排序
    default List<MusicVO> Host(Integer num) {
        MPJLambdaWrapper<Music> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll()
                .eq(Music::getStatus, "上线中")
                .leftJoin(User.class, User::getId, Music::getAuthor_id)
                .selectAs(User::getNickname, "author_name")
                .orderByDesc(Music::getClicks)
                .last("limit " + num);
        return this.selectJoinList(MusicVO.class, wrapper);
    }

    //约定数量,获取最新发布
    default List<MusicVO> Latest() {
        MPJLambdaWrapper<Music> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll()
                .eq(Music::getStatus, "上线中")
                .leftJoin(User.class, User::getId, Music::getAuthor_id)
                .selectAs(User::getNickname, "author_name")
                .orderByDesc(Music::getId)
                .last("limit 20");
        return this.selectJoinList(MusicVO.class, wrapper);
    }

    //获取个人作品
    default List<MusicVO> myWorks(Integer author_id) {
        MPJLambdaWrapper<Music> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll()
                .eq(Music::getAuthor_id, author_id)
                .leftJoin(User.class, User::getId, Music::getAuthor_id)
                .selectAs(User::getNickname, "author_name")
                .orderByDesc(Music::getId);
        return this.selectJoinList(MusicVO.class, wrapper);
    }
}
