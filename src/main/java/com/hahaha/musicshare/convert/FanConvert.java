package com.hahaha.musicshare.convert;

import com.hahaha.musicshare.model.dto.FanDTO;
import com.hahaha.musicshare.model.entity.Fan;
import com.hahaha.musicshare.model.vo.FanVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FanConvert {

    FanConvert INSTANCE = Mappers.getMapper(FanConvert.class);

    Fan convert(FanDTO fanDTO);
}
