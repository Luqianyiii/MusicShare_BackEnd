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

    @RequestMapping(value = "/sendSms", method = {RequestMethod.POST, RequestMethod.GET})
    @Operation(summary = "发送短信")
    public Result<Object> sendSms(@RequestParam("phone") String phone) {
        communicationService.sendSms(phone);
        return Result.ok();
    }

    @RequestMapping(value = "/changePhone", method = {RequestMethod.POST, RequestMethod.PUT})
    @Operation(summary = "换绑手机号")
    public Result<String> bindPhone(
            @RequestParam("phone") String phone,
            @RequestParam("code") String code,
            @RequestHeader("Authorization") String accessToken)
    {
        return Result.ok(communicationService.bindPhone(phone, code, accessToken));
    }
}
