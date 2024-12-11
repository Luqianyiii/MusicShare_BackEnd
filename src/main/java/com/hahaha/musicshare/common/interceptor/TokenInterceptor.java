package com.hahaha.musicshare.common.interceptor;

import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.common.cache.TokenStoreCache;
import com.hahaha.musicshare.common.constant.Constant;
import com.hahaha.musicshare.common.exception.ErrorCode;
import com.hahaha.musicshare.common.exception.ServerException;
import com.hahaha.musicshare.model.vo.UserLoginVO;
import com.hahaha.musicshare.service.AuthService;
import com.hahaha.musicshare.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@AllArgsConstructor
@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final TokenStoreCache tokenStoreCache;
    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取token
        String accessToken = JwtUtil.getAccessToken(request);
        if (StringUtils.isBlank(accessToken)) {
            throw new ServerException(ErrorCode.UNAUTHORIZED);
        }
        // 打印请求头
        System.out.println(request.getHeaderNames());
        // 校验token
        if (!JwtUtil.validate(accessToken)) {
            throw new ServerException(ErrorCode.UNAUTHORIZED);
        }

        UserLoginVO user = tokenStoreCache.getUser(accessToken);

        // 保存⽤户id到上下⽂
        RequestContext.put(Constant.USER_ID, user.getId());
//        保存角色信息到上下文
        RequestContext.put(Constant.USER_ROLE, user.getRemark());
        return true;
    }
}
