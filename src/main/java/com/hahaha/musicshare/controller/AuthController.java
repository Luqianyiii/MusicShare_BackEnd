package com.hahaha.musicshare.controller;

import com.hahaha.musicshare.common.result.Result;
import com.hahaha.musicshare.model.vo.UserLoginVO;
import com.hahaha.musicshare.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "登录认证层")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @RequestMapping(value = "/login_by_code", method = {RequestMethod.POST, RequestMethod.GET})
    @Operation(summary = "验证码登录")
    public Result<UserLoginVO> loginByCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        return Result.ok(authService.loginByCode(phone, code));
    }

    @RequestMapping(value = "/login_by_password", method = {RequestMethod.POST, RequestMethod.GET})
    @Operation(summary = "密码登录")
    public Result<UserLoginVO> loginByPassWord(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        return Result.ok(authService.loginByPassword(phone, password));
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    @Operation(summary = "登出")
    public Result<Object> logout() {
        authService.logout();
        return Result.ok();
    }
}
