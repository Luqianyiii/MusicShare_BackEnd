package com.hahaha.musicshare.common.interceptor;

import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.common.exception.ErrorCode;
import com.hahaha.musicshare.common.exception.ServerException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@AllArgsConstructor
@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取用户角色
        String role = RequestContext.getUserRole();
        if (role == null) {
            throw new ServerException(ErrorCode.UNAUTHORIZED);
        }

        // 根据角色进行相应的验证
        if (!"admin".equals(role)) {
            throw new ServerException(ErrorCode.FORBIDDEN);
        }

        return true;
    }
}
