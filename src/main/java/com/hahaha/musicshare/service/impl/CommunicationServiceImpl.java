package com.hahaha.musicshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.hahaha.musicshare.common.cache.RedisCache;
import com.hahaha.musicshare.common.cache.RedisKeys;
import com.hahaha.musicshare.common.cache.TokenStoreCache;
import com.hahaha.musicshare.common.config.CloopenConfig;
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
import org.springframework.stereotype.Service;

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


    @Override
    public void bindPhone(String phone, String code, String accessToken) {
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
        // 重新设置⼿机号
        User user = baseMapper.selectById(userLogin.getId());
        user.setPhone(phone);
        if (baseMapper.updateById(user) < 1) {
            throw new ServerException(ErrorCode.OPERATION_FAIL);
        }
    }
}
