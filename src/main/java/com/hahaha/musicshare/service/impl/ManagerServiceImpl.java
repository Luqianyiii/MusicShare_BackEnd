package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.mapper.UserMapper;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserInfoVO;
import com.hahaha.musicshare.service.ManagerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ManagerServiceImpl extends ServiceImpl<UserMapper, User> implements ManagerService {
    @Override
    public List<UserInfoVO> getAllUser() {
        return List.of();
    }

    @Override
    public void deleteUser(Integer user_id) {

    }

    @Override
    public List<UserInfoVO> getUserByKeyword() {
        return List.of();
    }
}
