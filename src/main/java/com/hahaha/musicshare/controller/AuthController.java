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
@Tag(name = "认证接⼝")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "⼿机号登录")
    public Result<UserLoginVO> loginByPhone(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        return Result.ok(authService.loginByPhone(phone, code));
    }

    @PostMapping("/logout")
    @Operation(summary = "登出")
    public Result<Object> logout() {
        authService.logout();
        return Result.ok();
    }

    //    使⽤场景:
//    后⾯换绑⼿机号：需要先⽤旧的⼿机号登录，得到 token，然后给新的⼿机号发送验证码，使
//    ⽤新的⼿机号和第⼆次的验证码，携带请求头进⾏⼿机号的换绑
    @PostMapping("/bindPhone")
    @Operation(summary = "绑定⼿机号")
    public Result<String> bindPhone(@RequestParam("phone") String phone,
                                    @RequestParam("code") String code,
                                    @RequestHeader("Authorization") String accessToken) {
        authService.bindPhone(phone, code, accessToken);
        return Result.ok();
    }
}
