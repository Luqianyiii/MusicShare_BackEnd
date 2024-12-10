package com.hahaha.musicshare.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.common.config.OssConfig;
import com.hahaha.musicshare.common.exception.ErrorCode;
import com.hahaha.musicshare.common.exception.ServerException;
import com.hahaha.musicshare.convert.UserConvert;
import com.hahaha.musicshare.mapper.UserMapper;
import com.hahaha.musicshare.model.dto.UserEditDTO;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserInfoVO;
import com.hahaha.musicshare.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // 允许上传⽂件(图⽚)的格式
    static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg"
            , ".jpeg", ".gif", ".png"};
    private final OssConfig ossConfig;
    @Override
    public UserInfoVO userInfo() {
        Integer userId = RequestContext.getUserId();
        // 查询数据库
        User user = baseMapper.selectById(userId);
        if (user == null) {
            log.error("⽤户不存在, userId: {}", userId);
            throw new ServerException(ErrorCode.USER_NOT_EXIST);
        }

        return UserConvert.INSTANCE.convert(user);
    }
    @Override
    public UserInfoVO updateInfo(UserEditDTO userEditDTO) {
        Integer userId = RequestContext.getUserId();
        userEditDTO.setId(userId);
        User user = UserConvert.INSTANCE.convert(userEditDTO);
        if (user.getId() == null) {
            throw new ServerException(ErrorCode.PARAMS_ERROR);
        }
        try {
            if (baseMapper.updateById(user) < 1) {
                throw new ServerException("修改失败");
            }
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
        return this.userInfo();
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        String returnImgUrl;
        // 校验图⽚格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(),
                    type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            // 如果图⽚格式不合法
            throw new ServerException("图⽚格式不正确");
        }
        // 获取⽂件原名称
        String originalFilename = file.getOriginalFilename();
        // 获取⽂件类型
        assert originalFilename != null;
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 新⽂件名称
        String newFileName = UUID.randomUUID() + fileType;
        // 构建⽇期路径, 例如：OSS⽬标⽂件夹/2024/04/31/⽂件名
        String filePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        // ⽂件上传的路径地址
        String uploadUrl = filePath + "/" + newFileName;
        // 获取⽂件输⼊流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
         * 现在阿⾥云OSS 默认图⽚上传ContentType是image/jpeg
         * 也就是说，获取图⽚链接后，图⽚是下载链接，⽽并⾮在线浏览链接，
         * 因此，这⾥在上传的时候要解决ContentType的问题，将其改为image/jpg
         */
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("image/jpg");
        //读取配置⽂件中的属性
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();
        String bucketName = ossConfig.getBucketName();
        // 创建 OssClient
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId
                , accessKeySecret);
        //⽂件上传⾄阿⾥云OSS
        ossClient.putObject(bucketName, uploadUrl, inputStream, meta);
        // 获取⽂件上传后的图⽚返回地址
        returnImgUrl = "https://" + bucketName + "." + endpoint + "/" + uploadUrl;
//        TODO 部分业务逻辑待测试
        Integer userId = RequestContext.getUserId();
        //将用户头像更新到数据库
        User user = new User();
        user.setId(userId);
        user.setAvatar(returnImgUrl);
        baseMapper.updateById(user);
        return returnImgUrl;
    }
}
