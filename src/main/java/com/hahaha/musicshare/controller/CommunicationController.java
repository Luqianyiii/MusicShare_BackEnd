package com.hahaha.musicshare.controller;

import com.hahaha.musicshare.common.result.Result;
import com.hahaha.musicshare.service.CommunicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "通讯服务层")
@RestController
@RequestMapping("/communication")
@AllArgsConstructor
public class CommunicationController {
    private final CommunicationService communicationService;
    @PostMapping("/sendSms")
    @Operation(summary = "发送短信")
    public Result<Object> sendSms(@RequestParam("phone") String phone) {
        communicationService.sendSms(phone);
        return Result.ok();
    }


//    后⾯换绑⼿机号：需要先⽤旧的⼿机号登录，得到 token，然后给新的⼿机号发送验证码，使
//    ⽤新的⼿机号和第⼆次的验证码，携带请求头进⾏⼿机号的换绑

    @PostMapping("/changePhone")
    @Operation(summary = "换绑⼿机号")
    public Result<String> bindPhone(
            @RequestParam("phone") String phone,
            @RequestParam("code") String code,
            @RequestHeader("Authorization") String accessToken)
    {
        return Result.ok(communicationService.bindPhone(phone, code, accessToken));
    }

}
