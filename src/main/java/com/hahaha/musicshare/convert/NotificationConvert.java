package com.hahaha.musicshare.convert;

import com.hahaha.musicshare.model.entity.Notification;
import com.hahaha.musicshare.model.vo.NotificationVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationConvert {
    NotificationConvert INSTANCE = Mappers.getMapper(NotificationConvert.class);

    //将 Notification 对象转换为 NotificationVO 对象
    NotificationVO convert(Notification notification);
}
