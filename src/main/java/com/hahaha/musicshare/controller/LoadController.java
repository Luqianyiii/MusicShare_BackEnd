package com.hahaha.musicshare.controller;

import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.common.result.Result;
import com.hahaha.musicshare.model.vo.UserInfoVO;
import com.hahaha.musicshare.service.LoadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/load")
@AllArgsConstructor
@Tag(name = "数据加载层")
public class LoadController {

    private final LoadService loadService;

    @GetMapping("info")
    @Operation(summary = "查询⽤户信息")
    public Result<UserInfoVO> userInfo() {
        return Result.ok(loadService.loadUser(RequestContext.getUserId()));
    }
}
