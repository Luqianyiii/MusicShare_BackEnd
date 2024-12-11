package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.model.vo.UserInfoVO;
import com.hahaha.musicshare.service.LoadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class LoadServiceImpl   extends ServiceImpl implements LoadService {
    @Override
    public UserInfoVO loadUser(int userId) {

        return null;
    }

}
