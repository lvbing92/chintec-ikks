package com.chintec.ikks.message.service.impl;


import com.alibaba.alicloud.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.chintec.ikks.common.enums.CommonCodeEnum;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PhoneUtils;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.message.service.ISmsServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/18 12:47
 */
@Service
public class SmsServiceImpl implements ISmsServices {
    @Autowired
    private ISmsService ismsService;
    @Value("${spring.cloud.alicloud.sms.sign}")
    private String sign;
    @Value("${spring.cloud.alicloud.sms.template-code}")
    private String templateCode;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送验证码，并保存5分钟有效期
     *
     * @param phone 手机号
     * @return ResultResponse
     */
    @Override
    public ResultResponse sendSms(String phone) {
        AssertsUtil.isTrue(!PhoneUtils.phoneCheck(phone), "请输入正确的手机号码", CommonCodeEnum.PARAMS_ERROR_CODE.getCode());
        ValueOperations<String, String> stringObjectValueOperations = redisTemplate.opsForValue();
        Long expire = redisTemplate.getExpire(phone);
        AssertsUtil.isTrue(!StringUtils.isEmpty(expire) && expire > 240, "请勿重复发送验证码");
        Integer code = getCode();
        SendSmsResponse sendSmsResponse = sendSms(phone, code);
//        log.info(sendSmsResponse.getMessage());
        AssertsUtil.isTrue(!"OK".equals(sendSmsResponse.getMessage()), "短信发送失败");
        stringObjectValueOperations.set(phone, String.valueOf(code));
        redisTemplate.expire(phone, 300, TimeUnit.SECONDS);
        return ResultResponse.successResponse("发送信息成功");
    }

    /**
     * 检测验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return ResultResponse
     */
    @Override
    public ResultResponse checkCode(String phone, String code) {
        AssertsUtil.isTrue(StringUtils.isEmpty(code), "验证码不能为空");
        ValueOperations<String, String> stringObjectValueOperations = redisTemplate.opsForValue();
        String s = stringObjectValueOperations.get(phone);
        AssertsUtil.isTrue(StringUtils.isEmpty(s), "验证码错误，请重试", CommonCodeEnum.PARAMS_ERROR_CODE.getCode());
        return ResultResponse.successResponse("验证码验证成功");
    }

    /**
     * 获取随机数作为验证
     *
     * @return code 验证码
     */
    private Integer getCode() {
        return (int) ((Math.random() * 9 + 1) * 10000);
    }

    /**
     * 调取阿里云 sms 接口
     *
     * @param phone 手机号
     * @param code  验证码
     * @return sendSmsResponse
     */
    private SendSmsResponse sendSms(String phone, Integer code) {
        SendSmsRequest request = new SendSmsRequest();
        // Required:the mobile number
        request.setPhoneNumbers(phone);
        // Required:SMS-SignName-could be found in sms console
        request.setSignName(sign);
        // Required:Template-could be found in sms console
        request.setTemplateCode(templateCode);
        // Required:The param of sms template.For exmaple, if the template is "Hello,your verification code is ${code}". The param should be like following value
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = ismsService.sendSmsRequest(request);
        } catch (ClientException e) {
//            log.error(e.getErrMsg());
            sendSmsResponse = new SendSmsResponse();
            sendSmsResponse.setMessage("发送短消息失败");
        }
        return sendSmsResponse;
    }

}
