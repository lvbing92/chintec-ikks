package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.mapper.OauthClientDetailsMapper;
import com.chintec.ikks.auth.service.IOauthClientDetailsService;
import com.chintec.ikks.common.entity.OauthClientDetails;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-27
 */
@Service
public class OauthClientDetailsServiceImpl extends
        ServiceImpl<OauthClientDetailsMapper, OauthClientDetails>
        implements IOauthClientDetailsService {

}
