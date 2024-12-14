package com.hahaha.musicshare.convert;

import com.hahaha.musicshare.model.dto.FanDTO;
import com.hahaha.musicshare.model.entity.Fan;
import com.hahaha.musicshare.model.vo.FanVO;
import org.mapstruct.factory.Mappers;

public interface FanConvert {

    FanConvert INSTANCE = Mappers.getMapper(FanConvert.class);

    FanVO convert(Fan fan);
    Fan convert(FanVO fanVO);
    Fan convert(FanDTO fanDTO);
}
