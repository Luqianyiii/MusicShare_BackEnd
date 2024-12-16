package com.hahaha.musicshare.convert;

import com.hahaha.musicshare.model.dto.UserEditDTO;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserConvert {

    //获取 UserConvert 实例，由 MapStruct ⾃动⽣成实现类并提供实例
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    //将 UserEditDTO 对象转换为 User 对象
    User convert(UserEditDTO dto);

    //将 User 对象转换为 UserInfoVO 对象
    UserInfoVO convert(User user);

    default List<UserInfoVO> convert(List<User> users) {
        List<UserInfoVO> res = new ArrayList<>();
        for (User user : users) {
            UserInfoVO temp = convert(user);
            res.add(temp);
        }
        return res;
    }

}
