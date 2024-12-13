package com.hahaha.musicshare.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.common.cache.RedisCache;
import com.hahaha.musicshare.common.cache.RedisKeys;
import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.common.cache.TokenStoreCache;
import com.hahaha.musicshare.common.config.CloopenConfig;
import com.hahaha.musicshare.common.config.OssConfig;
import com.hahaha.musicshare.common.exception.ErrorCode;
import com.hahaha.musicshare.common.exception.ServerException;
import com.hahaha.musicshare.convert.UserConvert;
import com.hahaha.musicshare.mapper.UserMapper;
import com.hahaha.musicshare.model.dto.UserEditDTO;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserInfoVO;
import com.hahaha.musicshare.model.vo.UserLoginVO;
import com.hahaha.musicshare.service.UserService;
import com.hahaha.musicshare.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public UserInfoVO userInfo() {
        Integer userId = RequestContext.getUserId();
        // 查询数据库
        User user = baseMapper.selectById(userId);
        if (user == null) {
            log.error("⽤户不存在, userId: {}", userId);
            throw new ServerException(ErrorCode.USER_NOT_EXIST);
        }

        return UserConvert.INSTANCE.convert(user);
    }
    @Override
    public UserInfoVO updateInfo(UserEditDTO userEditDTO) {
        Integer userId = RequestContext.getUserId();
        userEditDTO.setId(userId);
        User user = UserConvert.INSTANCE.convert(userEditDTO);
        if (user.getId() == null) {
            throw new ServerException(ErrorCode.PARAMS_ERROR);
        }
        try {
            if (baseMapper.updateById(user) < 1) {
                throw new ServerException("修改失败");
            }
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
        return this.userInfo();
    }


}
