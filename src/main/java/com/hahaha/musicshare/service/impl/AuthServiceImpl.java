package com.hahaha.musicshare.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.common.cache.RedisCache;
import com.hahaha.musicshare.common.cache.RedisKeys;
import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.common.cache.TokenStoreCache;
import com.hahaha.musicshare.common.exception.ErrorCode;
import com.hahaha.musicshare.common.exception.ServerException;
import com.hahaha.musicshare.mapper.UserMapper;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserLoginVO;
import com.hahaha.musicshare.service.AuthService;
import com.hahaha.musicshare.utils.AIAssistantUtils;
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
    public UserLoginVO loginByCode(String phone, String code) {
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
//         调用千问大模型，生成随机用户名
            user.setNickname(AIAssistantUtils.createName());
            user.setPhone(phone);
            user.setRemark("user");
            baseMapper.insert(user);
        }
        return getUserLoginVO(user);
    }

    private UserLoginVO getUserLoginVO(User user) {
        // 构造token
        String accessToken = JwtUtil.createToken(user.getId());
        // 构造登陆返回vo
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setId(user.getId());
        userLoginVO.setPhone(user.getPhone());
        userLoginVO.setAccessToken(accessToken);
        userLoginVO.setRemark(user.getRemark());
        tokenStoreCache.saveUser(accessToken, userLoginVO);
        return userLoginVO;
    }

    @Override
    public UserLoginVO loginByPassword(String phone, String password) {
        // 根据⼿机号获取⽤户
        User user = baseMapper.getByPhone(phone);
        // 判断⽤户是否存在
        if (ObjectUtils.isEmpty(user)) {
            throw new ServerException(ErrorCode.USER_NOT_EXIST);
        } else if (!user.getPassword().equals(password)) {
            throw new ServerException(ErrorCode.PASSWORD_ERROR);
        }
        // 构造token
        return getUserLoginVO(user);
    }


    @Override
    public void logout() {
        // 从上下⽂中获取userId，然后获取redisKey
        String cacheKey = RedisKeys.getUserIdKey(RequestContext.getUserId());
        // 通过userId，获取redis中的 accessToken
        String accessToken = (String) redisCache.get(cacheKey);
        // 删除缓存中的 token
        redisCache.delete(cacheKey);
        // 删除缓存中的⽤户信息
        tokenStoreCache.deleteUser(accessToken);
    }


}
