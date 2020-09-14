package com.chintec.ikks.message.service;

import com.chintec.ikks.common.util.ResultResponse;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/18 12:46
 */
public interface ISmsServices {
    /**
     * 发送短信
     *
     * @param phone 手机号
     * @return resultResponse
     */
    ResultResponse sendSms(String phone);

    /**
     * 检验验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return resultResponse
     */
    ResultResponse checkCode(String phone, String code);
}
