package com.hahaha.musicshare.controller;

import com.hahaha.musicshare.common.result.Result;
import com.hahaha.musicshare.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "基础服务")
@RestController
@RequestMapping("/common")
@AllArgsConstructor
public class CommonController {
    private final CommonService commonService;
    @PostMapping("/sendSms")
    @Operation(summary = "发送短信")
    public Result<Object> sendSms(@RequestParam("phone") String phone) {
        commonService.sendSms(phone);
        return Result.ok();
    }
    @PostMapping(value = "/upload/img")
    @Operation(summary = "图⽚上传")
    public Result<String> upload(@RequestBody MultipartFile file) {
        return Result.ok(commonService.upload(file));
    }
}
