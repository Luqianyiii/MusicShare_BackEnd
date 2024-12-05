package com.hahaha.musicshare.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.common.cache.RedisCache;
import com.hahaha.musicshare.common.cache.RedisKeys;
import com.hahaha.musicshare.common.cache.TokenStoreCache;
import com.hahaha.musicshare.common.exception.ErrorCode;
import com.hahaha.musicshare.common.exception.ServerException;
import com.hahaha.musicshare.enums.AccountStatusEnum;
import com.hahaha.musicshare.mapper.UserMapper;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserLoginVO;
import com.hahaha.musicshare.service.AuthService;
import com.hahaha.musicshare.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {
    private final RedisCache redisCache;
    private final TokenStoreCache tokenStoreCache;

    @Override
    public UserLoginVO loginByPhone(String phone, String code) {
        // 获取验证码cacheKey
        String smsCacheKey = RedisKeys.getSmsKey(phone);
        // 从redis中获取验证码
        Integer redisCode = (Integer) redisCache.get(smsCacheKey);
        // 校验验证码合法性
        if (ObjectUtils.isEmpty(redisCode) || !redisCode.toString().equals
                (code)) {
            throw new ServerException(ErrorCode.SMS_CODE_ERROR);
        }
        // 删除⽤过的验证码
        redisCache.delete(smsCacheKey);
        // 根据⼿机号获取⽤户
        User user = baseMapper.getByPhone(phone);
        // 判断⽤户是否注册过，如果user为空代表未注册，进⾏注册。否则开启登录流程
        if (ObjectUtils.isEmpty(user)) {
            log.info("⽤户不存在，创建⽤户, phone: {}", phone);
            user = new User();
            user.setNickname(phone);
            user.setPhone(phone);
            baseMapper.insert(user);
        }
        // 构造token
        String accessToken = JwtUtil.createToken(user.getId());
        // 构造登陆返回vo
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setPkId(user.getId());
        userLoginVO.setPhone(user.getPhone());
        userLoginVO.setAccessToken(accessToken);
        tokenStoreCache.saveUser(accessToken, userLoginVO);
        return userLoginVO;
    }
}
