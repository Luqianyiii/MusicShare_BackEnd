package com.hahaha.musicshare.convert;

import com.hahaha.musicshare.model.dto.MusicDTO;
import com.hahaha.musicshare.model.entity.Music;
import com.hahaha.musicshare.model.vo.MusicVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MusicConvert {
    MusicConvert INSTANCE = Mappers.getMapper(MusicConvert.class);

    //将 MusicDTO 对象转换为 Music 对象
    Music convert(MusicDTO dto);

    //将 Music 对象转换为 MusicVO 对象
    MusicVO convert(Music music);
}