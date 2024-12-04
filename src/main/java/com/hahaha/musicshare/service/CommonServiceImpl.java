package com.hahaha.musicshare.service;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.hahaha.musicshare.common.cache.RedisCache;
import com.hahaha.musicshare.common.cache.RedisKeys;
import com.hahaha.musicshare.common.config.CloopenConfig;
import com.hahaha.musicshare.common.exception.ErrorCode;
import com.hahaha.musicshare.common.exception.ServerException;
import com.hahaha.musicshare.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CommonServiceImpl implements CommonService {
    private final CloopenConfig cloopenConfig;
    private final RedisCache redisCache;

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
}
