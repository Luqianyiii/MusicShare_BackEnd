package com.hahaha.musicshare.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.hahaha.musicshare.common.cache.RedisCache;
import com.hahaha.musicshare.common.cache.RedisKeys;
import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.common.cache.TokenStoreCache;
import com.hahaha.musicshare.common.config.CloopenConfig;
import com.hahaha.musicshare.common.config.OssConfig;
import com.hahaha.musicshare.common.exception.ErrorCode;
import com.hahaha.musicshare.common.exception.ServerException;
import com.hahaha.musicshare.mapper.UserMapper;
import com.hahaha.musicshare.model.entity.User;
import com.hahaha.musicshare.model.vo.UserLoginVO;
import com.hahaha.musicshare.service.CommunicationService;
import com.hahaha.musicshare.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CommunicationServiceImpl extends ServiceImpl<UserMapper, User> implements CommunicationService {
    private final CloopenConfig cloopenConfig;
    private final RedisCache redisCache;
    private final TokenStoreCache tokenStoreCache;
    private final OssConfig ossConfig;
    // 允许上传⽂件(图⽚)的格式
    static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg"
            , ".jpeg", ".gif", ".png"};

    @Override
    public void sendSms(String phone) {
        // 校验⼿机号合法性
        if (!CommonUtils.checkPhone(phone)) {
            throw new ServerException(ErrorCode.PARAMS_ERROR);
        }
        // ⽣成随机验证码
        int code = CommonUtils.generateCode();
        // redis缓存验证码
        redisCache.set(RedisKeys.getSmsKey(phone), code, 60);
        // 调⽤内部⽅法发送短信
        boolean result = cloopenSendSms(phone, code);
        if (result) {
            log.info(" ============= 短信发送成功 ============= ");
        }
    }

    /**
     * cloopen 发送短信
     *
     * @param phone 电话public class CommonServiceImpl {
     *              }
     * @param code  验证码
     * @return boolean
     */
    private boolean cloopenSendSms(String phone, int code) {
        try {
            log.info(" ============= 创建短信发送通道中 ============= \nphone is {},code is {}", phone, code);
            CCPRestSmsSDK sdk = getSmsSDK();
            String templateId = cloopenConfig.getTemplateId();
            String[] datas = {String.valueOf(code), "1"};
            HashMap<String, Object> result = sdk.sendTemplateSMS(phone, templateId, datas, "1234", UUID.randomUUID().toString());
            if ("000000".equals(result.get("statusCode"))) {
                // 正常返回输出data包体信息（map）
                HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
                Set<String> keySet = data.keySet();
                for (String key : keySet) {
                    Object object = data.get(key);
                    log.info("{} = {}", key, object);
                }
            } else {
                // 异常返回输出错误码和错误信息
                log.error("错误码={} 错误信息= {}", result.get("statusCode")
                        , result.get("statusMsg"));
                throw new ServerException(ErrorCode.CODE_SEND_FAIL);
            }
        } catch (Exception e) {
            throw new ServerException(ErrorCode.CODE_SEND_FAIL);
        }
        return true;
    }

    private CCPRestSmsSDK getSmsSDK() {
        String serverIp = cloopenConfig.getServerIp();
        // 请求端⼝
        String serverPort = cloopenConfig.getPort();
        // 主账号,登陆云通讯⽹站后,可在控制台⾸⻚看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN

        String accountSId = cloopenConfig.getAccountSId();
        String accountToken = cloopenConfig.getAccountToken();
        // 请使⽤管理控制台中已创建应⽤的APPID
        String appId = cloopenConfig.getAppId();
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        return sdk;
    }


    @Override
    public void bindPhone(String phone, String code, String accessToken) {
        UserLoginVO userLogin = validateUpdate(phone, code, accessToken);
        // 重新设置⼿机号
        User user = baseMapper.selectById(userLogin.getId());
        user.setPhone(phone);
        if (baseMapper.updateById(user) < 1) {
            throw new ServerException(ErrorCode.OPERATION_FAIL);
        }
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
        Integer userId = RequestContext.getUserId();
        //将用户头像更新到数据库
        User user = new User();
        user.setId(userId);
        user.setAvatar(returnImgUrl);
        baseMapper.updateById(user);
        return returnImgUrl;
    }

    @Override
    public String updatePassword(String phone, String code, String password,String accessToken) {
        UserLoginVO userInfo = validateUpdate(phone,code, accessToken);
        // 更新密码
        User user = baseMapper.selectById(userInfo.getId());
        user.setPassword(password);
        if (baseMapper.updateById(user) < 1) {
            throw new ServerException(ErrorCode.OPERATION_FAIL);
        }
        return "ok";
    }

    private UserLoginVO validateUpdate(String phone, String code, String accessToken) {
        // 简单校验⼿机号合法性
        if (!CommonUtils.checkPhone(phone)) {
            throw new ServerException(ErrorCode.PARAMS_ERROR);
        }
        // 获取⼿机验证码，校验验证码正确性
        String redisCode = redisCache.get(RedisKeys.getSmsKey(phone)).toString
                ();
        if (ObjectUtils.isEmpty(redisCode) || !redisCode.equals(code)) {
            throw new ServerException(ErrorCode.SMS_CODE_ERROR);
        }
        // 删除验证码缓存
        redisCache.delete(RedisKeys.getSmsKey(phone));
        // 获取当前⽤户信息
        User userByPhone = baseMapper.getByPhone(phone);
        // 获取当前登录的⽤户信息
        UserLoginVO userLogin = tokenStoreCache.getUser(accessToken);
        // 判断新⼿机号是否存在⽤户
        if (ObjectUtils.isNotEmpty(userByPhone)) {
            // 存在⽤户，并且不是当前⽤户，抛出异常
            if (!userLogin.getId().equals(userByPhone.getId())) {
                throw new ServerException(ErrorCode.PHONE_IS_EXIST);
            }
            // 存在⽤户，并且是当前⽤户，提示⽤户⼿机号相同
            if (userLogin.getPhone().equals(phone)) {
                throw new ServerException(ErrorCode.THE_SAME_PHONE);
            }
        }
        return userLogin;
    }
}
