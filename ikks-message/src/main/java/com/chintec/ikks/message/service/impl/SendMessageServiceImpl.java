package com.chintec.ikks.message.service.impl;

import com.chintec.ikks.common.entity.MessageRec;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.message.service.ISendMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 此类为实现类
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/24 14:38
 */
@Service
@Slf4j
public class SendMessageServiceImpl implements ISendMessageService {
    @Override
    public ResultResponse sendMessages(MessageRec messageRec) {
        return null;
    }
}
